package com.example.webdev.controller;


import com.example.webdev.model.FeedDTO;
import com.example.webdev.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/no-auth")
public class NoAuthController {

  @Autowired
  private PostService postService;

  @GetMapping("/feed")
  public FeedDTO getFeedForNoAuth(){
    return postService.getFeed(1);
  }



}
