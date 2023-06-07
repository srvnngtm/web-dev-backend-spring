package com.example.webdev.controller;

import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.FitUser;
import com.example.webdev.model.User;
import com.example.webdev.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
//@CrossOrigin
public class HomeController {

  @Autowired
  private UserService userService;


  @GetMapping("")
  public String getHomeText(@RequestAttribute("user") User loggedUser){

    return "Welcome " + loggedUser.getUsername();

  }



  @GetMapping("/basic")
  public BasicUserDetails getBasicUserDetails(@RequestAttribute("user") User loggedUser){

    BasicUserDetails basicUserDetails = userService.getBasicUserDetailsByUserId(loggedUser.getId());


    return basicUserDetails;
  }





}
