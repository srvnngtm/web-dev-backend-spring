package com.example.webdev.filters;

import com.example.webdev.configs.CustomUserDetails;
import com.example.webdev.configs.CustomUserDetailsService;
import com.example.webdev.model.User;
import com.example.webdev.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {



    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
      String authorization = httpServletRequest.getHeader("Authorization");
      String token = null;
      String userName = null;
      UserDetails userDetails = null;
      User loggedUser = null;

      if(null != authorization && authorization.startsWith("Bearer ")) {
        token = authorization.substring(7);
        userName = jwtUtils.getUsernameFromToken(token);
      }

      if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
         userDetails
                = userService.loadUserByUsername(userName);


        if(jwtUtils.validateToken(token,userDetails)) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                  = new UsernamePasswordAuthenticationToken(userDetails,
                  null, userDetails.getAuthorities());

          usernamePasswordAuthenticationToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
          );

          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

      }

      if(Objects.nonNull(userDetails)){

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        httpServletRequest.setAttribute("user", customUserDetails.getUser());
      }

      filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}
