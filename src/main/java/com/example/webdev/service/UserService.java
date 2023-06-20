package com.example.webdev.service;

import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.FitUser;
import com.example.webdev.model.PostUserLikeMap;
import com.example.webdev.model.User;
import com.example.webdev.model.UserDTO;
import com.example.webdev.model.UserFollowDTO;
import com.example.webdev.model.UserProfileDTO;
import com.example.webdev.model.UserRegistrationDTO;
import com.example.webdev.model.UserUserFollowMap;
import com.example.webdev.repository.FitUserRepository;
import com.example.webdev.repository.UserRepository;
import com.example.webdev.repository.UserUserFollowMapRepository;
import com.example.webdev.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import jakarta.transaction.Transactional;

@Service

public class UserService {

  @Autowired
  private FitUserRepository fitUserRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserUserFollowMapRepository userUserFollowMapRepository;


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

  public UserProfileDTO getUserProfileDetails(User loggedUser, Integer userId){

    User user = userRepository.findById(userId).orElse(null);

    if(Objects.isNull(user)){
      throw new IllegalArgumentException("unable to find user");
    }

    FitUser fitUser = fitUserRepository.getFitUsersByUserId(user.getId());

    if(Objects.isNull(fitUser)){
      throw new IllegalArgumentException("unable to find fit user");
    }


    UserProfileDTO userProfileDTO = UserProfileDTO.builder()
            .user(user)
            .fitUser(fitUser)
            .isFollowing(false)
            .build();

    Optional<UserUserFollowMap> userUserFollowMap =
            userUserFollowMapRepository.findByUserIdAndAndFollowUserId(loggedUser.getId(), userId);


    userUserFollowMap.ifPresent(userFollowMap -> userProfileDTO.setIsFollowing(userFollowMap.isFollowing()));

    return userProfileDTO;
  }



  public void userRegistration(UserRegistrationDTO  userRegistrationDTO) throws  IllegalArgumentException{

    User user = User.builder()
            .firstName(userRegistrationDTO.getFirstName())
            .lastName(userRegistrationDTO.getLastName())
            .username(userRegistrationDTO.getUserName())
            .password( new BCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()))
            .role("UNASSIGNED")
            .build();

    try{
      user = userRepository.save(user);
    }catch (Exception e){
      throw new IllegalArgumentException("Cannot create User due to " + e.getMessage());
    }

    if(userRegistrationDTO.getRole().equals("USER")){

      if(Objects.isNull(userRegistrationDTO.getHeight())){
        throw new IllegalArgumentException("Height cannot be empty for Fit User");
      }

      if(Objects.isNull(userRegistrationDTO.getWeight())){
        throw new IllegalArgumentException("Weight cannot be empty for Fit User");
      }

      if(Objects.isNull(userRegistrationDTO.getProfilePicture() )){
        throw new IllegalArgumentException("Please upload a profile picture to interact with other " +
                "users in the platform");
      }

      FitUser fitUser = FitUser.builder()
              .userId(user.getId())
              .height(userRegistrationDTO.getHeight())
              .weight(userRegistrationDTO.getWeight())
              .profilePicture(userRegistrationDTO.getProfilePicture())
              .build();

      fitUserRepository.save(fitUser);
      user.setRole("USER");
      userRepository.save(user);

    }
  }



public void followUser(UserFollowDTO userFollowDTO ){

  Optional<UserUserFollowMap> followMap =
          userUserFollowMapRepository.findByUserIdAndAndFollowUserId(userFollowDTO.getUserId(),
          userFollowDTO.getFollowUserId());


  if(followMap.isPresent()){
    UserUserFollowMap userUserFollowMap = followMap.get();
    userUserFollowMap.setFollowing(userFollowDTO.getIsFollowing());
    userUserFollowMapRepository.save(userUserFollowMap);

  }else{

    UserUserFollowMap userUserFollowMap = UserUserFollowMap.builder()
            .userId(userFollowDTO.getUserId())
            .followUserId(userFollowDTO.getFollowUserId())
            .isFollowing(userFollowDTO.getIsFollowing())
            .build();

    userUserFollowMapRepository.save(userUserFollowMap);

  }




}

  public List<UserDTO> getUserFollowing(Integer userId, Boolean isfollowing){

    List<Integer> userIds;

    if(isfollowing){
      // get users he is following
      List<UserUserFollowMap> followerMap = userUserFollowMapRepository.findAllByUserId(userId);
      userIds  =
              followerMap.stream().filter(UserUserFollowMap::isFollowing).map(UserUserFollowMap::getFollowUserId).toList();

    }else{

      // get users who follow him
      List<UserUserFollowMap> followerMap = userUserFollowMapRepository.findAllByFollowUserId(userId);
      userIds = followerMap.stream().map(UserUserFollowMap::getUserId).toList();

    }


    List<User> userList = userRepository.findAllById(userIds);
    Map<Integer, User> userIdToUserMap =
            userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));


    List<FitUser> fitUsers = fitUserRepository.findAllByUserIdIn(userIds);

    Map<Integer, FitUser> userIdToFitUserMap =
            fitUsers.stream().collect(Collectors.toMap(FitUser::getUserId, Function.identity()));

    List<UserDTO> userDTOList = new ArrayList<>();

    for (Integer id : userIds) {
      FitUser fitUser = userIdToFitUserMap.getOrDefault(id, null);

      if(Objects.nonNull(fitUser)){
        UserDTO userDTO = UserDTO.builder()
                .firstName(userIdToUserMap.get(id).getFirstName())
                .lastName(userIdToUserMap.get(id).getLastName())
                .profilePicture(fitUser.getProfilePicture())
                .userId(id)
                .build();

        userDTOList.add(userDTO);
      }



    }

    return userDTOList;

  }


  public List<UserDTO> getAllUsers(){

    List<User> userList = userRepository.findAll();
    Map<Integer, User> userIdToUserMap =
            userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));


    List<FitUser> fitUsers = fitUserRepository.findAllByUserIdIn(
            userIdToUserMap.keySet().stream().toList());

    Map<Integer, FitUser> userIdToFitUserMap =
            fitUsers.stream().collect(Collectors.toMap(FitUser::getUserId, Function.identity()));

    List<UserDTO> userDTOList = new ArrayList<>();

    for (Integer id : userIdToUserMap.keySet()) {
      FitUser fitUser = userIdToFitUserMap.getOrDefault(id, null);

      if(Objects.nonNull(fitUser)){
        UserDTO userDTO = UserDTO.builder()
                .firstName(userIdToUserMap.get(id).getFirstName())
                .lastName(userIdToUserMap.get(id).getLastName())
                .profilePicture(fitUser.getProfilePicture())
                .userId(id)
                .build();


        userDTOList.add(userDTO);
      }



    }

    return userDTOList;

  }






}
