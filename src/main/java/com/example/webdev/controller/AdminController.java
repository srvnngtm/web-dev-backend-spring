package com.example.webdev.controller;

import com.example.webdev.model.User;
import com.example.webdev.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
//@CrossOrigin
public class AdminController {

  @Autowired
  private UserService userService;

  @GetMapping("")
  public String getAdminText(){
    return "Admin page sample text";
  }

  @PostMapping("/delete/{uid}")
  public void deleteUser(@PathVariable("uid") Integer userId){
    userService.deleteUser(userId);
  }

  @GetMapping("/trainers")
  public List<User> getAllPendingUsers(){
    return userService.getAllPendingTrainers();
  }

  @PostMapping("/approve/{uid}")
  public void approveTrainer(@PathVariable("uid") Integer userId){
    userService.approveTrainer(userId);
  }








}
