package com.example.webdev.service;

import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.FitUser;
import com.example.webdev.model.trainer.Workout;
import com.example.webdev.model.trainer.WorkoutRequest;
import com.example.webdev.repository.WorkoutRepository;
import com.example.webdev.repository.WorkoutRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkoutService {

  @Autowired
  private WorkoutRepository workoutRepository;

  @Autowired
  private WorkoutRequestRepository workoutRequestRepository;

  @Autowired
  private UserService userService;




  /**
   * User fetches the workout assigned to him. If theres nothing assigned, then return empty list.
   *
   * @param userId
   * @return
   */
  public List<Workout> getWorkoutsByUserId(Integer userId){

    List<Workout> workoutList = workoutRepository.findAllByUserId(userId);

    if(CollectionUtils.isEmpty(workoutList)){
      return Collections.emptyList();
    }

    return workoutList;
  }

  public void createNewWorkoutRequest(Integer userId){
    List<Workout> workoutList = workoutRepository.findAllByUserId(userId);

    if(!CollectionUtils.isEmpty(workoutList)){
      List<Integer> workoutIds = workoutList.stream().map(Workout::getId).toList();
      workoutRepository.deleteAllById(workoutIds);
    }

    Optional<WorkoutRequest> optionalWorkoutRequest = workoutRequestRepository.findByUserId(userId);

    if(optionalWorkoutRequest.isPresent()){
      WorkoutRequest workoutRequest = optionalWorkoutRequest.get();
      workoutRequest.setIsComplete(false);
      workoutRequestRepository.save(workoutRequest);
    }else{
      WorkoutRequest workoutRequest = WorkoutRequest.builder()
              .userId(userId)
              .isComplete(false)
              .build();
      workoutRequestRepository.save(workoutRequest);
    }

  }


  public List<FitUser> getRequestedUsers(){
    List<WorkoutRequest> allRequests = workoutRequestRepository.findAll();

    if(CollectionUtils.isEmpty(allRequests)){
      return Collections.emptyList();
    }

    allRequests = allRequests.stream().filter(e-> !e.getIsComplete()).toList();

    if(CollectionUtils.isEmpty(allRequests)){
      return Collections.emptyList();
    }

    List<Integer> userIds = allRequests.stream().map(WorkoutRequest::getUserId).toList();

    List<FitUser> allFitUserDetails = userService.getAllFitUserDetails(userIds);

    return allFitUserDetails;

  }


  public void addWorkoutsForUser( List<Workout> workoutList){

    if(CollectionUtils.isEmpty(workoutList)){
      return;
    }

    List<Workout> workouts = workoutRepository.saveAll(workoutList);
    Optional<WorkoutRequest> optionalWorkoutRequest = workoutRequestRepository.findByUserId(workoutList.get(0).getUserId());
    if(optionalWorkoutRequest.isPresent()){
      WorkoutRequest workoutRequest = optionalWorkoutRequest.get();
      workoutRequest.setIsComplete(true);
      workoutRequestRepository.save(workoutRequest);
    }

  }

}
