package com.example.webdev.service;

import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.FitUser;
import com.example.webdev.model.User;
import com.example.webdev.repository.FitUserRepository;
import com.example.webdev.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {

  @Autowired
  private FitUserRepository fitUserRepository;

  @Autowired
  private UserRepository userRepository;


  public FitUser getFitUserDetails(Integer userId){
    FitUser fitUser = fitUserRepository.getFitUsersByUserId(userId);
    return fitUser;
  }

  public BasicUserDetails getBasicUserDetailsByUserId(Integer userId){

    User user = userRepository.findById(userId).orElse(null);
    FitUser fitUser = fitUserRepository.getFitUsersByUserId(user.getId());

    BasicUserDetails basicUserDetails = BasicUserDetails.builder()
            .user(user)
            .fitUser(fitUser)
            .build();

    return basicUserDetails;

  }



}
