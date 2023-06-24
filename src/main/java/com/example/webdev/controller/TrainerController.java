package com.example.webdev.controller;

import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.FitUser;
import com.example.webdev.model.User;
import com.example.webdev.model.trainer.Workout;
import com.example.webdev.service.WorkoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

  @Autowired
  private WorkoutService workoutService;


  @GetMapping("/requests")
  public List<BasicUserDetails> getBasicUserDetails(@RequestAttribute("user") User loggedUser){
    List<BasicUserDetails> requestedUsers = workoutService.getRequestedUsers();
    return requestedUsers;
  }

  @PostMapping("/create-routine")
  public void createRoutineForUser(@RequestAttribute("user") User loggedUser,
                                            @RequestBody List<Workout> workoutList){

    workoutService.addWorkoutsForUser(workoutList);

  }








}
