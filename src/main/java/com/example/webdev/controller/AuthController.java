package com.example.webdev.controller;

import com.example.webdev.configs.CustomUserDetails;
import com.example.webdev.configs.CustomUserDetailsService;
import com.example.webdev.model.ApiResponse;
import com.example.webdev.model.JwtRequest;
import com.example.webdev.model.JwtResponse;
import com.example.webdev.model.UserRegistrationDTO;
import com.example.webdev.service.UserService;
import com.example.webdev.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.BadRequestException;

import jakarta.transaction.Transactional;

@RestController
//@CrossOrigin(origins = "http://127.0.01", maxAge = 3600)
//@RequestMapping("")
@RequestMapping("/authenticate")
public class AuthController {

  @Autowired
  private JWTUtils jwtUtils;

  @Autowired
  private AuthenticationManager authenticationManager;


  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private UserService userService;



  @PostMapping("/")
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


    String userRole = userDetails.getUserRole();
    return  new JwtResponse(token, userRole );

  }



  @PostMapping("/register")
  public ApiResponse authenticate(@RequestBody UserRegistrationDTO  userRegistrationDTO){

    try {
      userService.userRegistration(userRegistrationDTO);
    } catch (Exception e) {

      ApiResponse response = ApiResponse.builder()
              .code(400)
              .message(e.getMessage())
              .build();

      return response;

    }

    return ApiResponse.builder()
            .code(200)
            .message("Registration Successful")
            .build();

  }


  }
