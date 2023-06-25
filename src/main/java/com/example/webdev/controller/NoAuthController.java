package com.example.webdev.controller;


import com.example.webdev.model.FeedDTO;
import com.example.webdev.model.User;
import com.example.webdev.model.UserDTO;
import com.example.webdev.model.UserProfileDTO;
import com.example.webdev.service.PostService;
import com.example.webdev.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/no-auth")
public class NoAuthController {

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @GetMapping("/feed")
  public FeedDTO getFeedForNoAuth(){
    return postService.getFeed(1);
  }


  @GetMapping("/users")
  public List<UserDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/profile/{uid}")
  public UserProfileDTO getUserProfileDetails(@PathVariable Integer uid) {
    UserProfileDTO userProfileDetails = userService.getUserProfileDetails(null, uid);
    userProfileDetails.getUser().setPassword("");
    return userProfileDetails;
  }


}
