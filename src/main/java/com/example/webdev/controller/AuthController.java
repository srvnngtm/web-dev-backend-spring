package com.example.webdev.controller;

import com.example.webdev.configs.CustomUserDetails;
import com.example.webdev.configs.CustomUserDetailsService;
import com.example.webdev.model.JwtRequest;
import com.example.webdev.model.JwtResponse;
import com.example.webdev.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
public class AuthController {

  @Autowired
  private JWTUtils jwtUtils;

  @Autowired
  private AuthenticationManager authenticationManager;


  @Autowired
  private CustomUserDetailsService customUserDetailsService;



  @PostMapping("/authenticate")
  public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{


    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      jwtRequest.getUsername(),
                      jwtRequest.getPassword()
              )
      );
    } catch (Exception e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }

    final CustomUserDetails userDetails
            = (CustomUserDetails) customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

    final String token =
            jwtUtils.generateToken(userDetails);

    return  new JwtResponse(token);

  }

}
