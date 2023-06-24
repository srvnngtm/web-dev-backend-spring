package com.example.webdev.controller;

import com.example.webdev.model.ApiResponse;
import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.CreatePostDTO;
import com.example.webdev.model.FeedDTO;
import com.example.webdev.model.LikePostDTO;
import com.example.webdev.model.User;
import com.example.webdev.model.UserDTO;
import com.example.webdev.model.UserFollowDTO;
import com.example.webdev.model.UserProfileDTO;
import com.example.webdev.model.UserUpdateDTO;
import com.example.webdev.model.trainer.Workout;
import com.example.webdev.service.PostService;
import com.example.webdev.service.UserService;
import com.example.webdev.service.WorkoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
//@CrossOrigin
public class HomeController {

  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  @Autowired
  private WorkoutService workoutService;


  @GetMapping("")
  public String getHomeText(@RequestAttribute("user") User loggedUser) {
    return "Welcome " + loggedUser.getUsername();
  }


  @GetMapping("/basic")
  public BasicUserDetails getBasicUserDetails(@RequestAttribute("user") User loggedUser) {
    BasicUserDetails basicUserDetails = userService.getBasicUserDetailsByUserId(loggedUser.getId());
    return basicUserDetails;
  }


  @GetMapping("/profile/{uid}")
  public UserProfileDTO getUserProfileDetails(@RequestAttribute("user") User loggedUser,
                                              @PathVariable Integer uid) {
    UserProfileDTO userProfileDetails = userService.getUserProfileDetails(loggedUser, uid);
    return userProfileDetails;
  }


  @GetMapping("/feed")
  public FeedDTO getFeed(@RequestAttribute("user") User loggedUser) {
    FeedDTO feed = postService.getFeed(loggedUser.getId());
    return feed;
  }


  @PostMapping("/createPost")
  public void createPost(@RequestAttribute("user") User loggedUser,
                         @RequestBody CreatePostDTO createPostDTO) {

    postService.createPost(createPostDTO, loggedUser.getId());

  }

  @PostMapping("/deletePost/{pid}")
  public ApiResponse deletePost(@RequestAttribute("user") User loggedUser,
                                @PathVariable("pid") Integer postId) {

    try {
      postService.deletePost(loggedUser.getId(), postId);
    } catch (Exception e) {

      return ApiResponse.builder()
              .code(400)
              .message(e.getMessage())
              .build();
    }


    return ApiResponse.builder()
            .code(200)
            .message("Deleted successfully")
            .build();

  }


  @PostMapping("/updateLike")
  public void updatePostLike(@RequestAttribute("user") User loggedUser,
                             @RequestBody LikePostDTO likePostDTO) {
//  likePostDTO.setPostId(loggedUser.getId());
    postService.updateLikeForPost(likePostDTO);
  }


  @GetMapping("/followers")
  public List<UserDTO> getFollowers(@RequestAttribute("user") User loggedUser) {
    return userService.getUserFollowing(loggedUser.getId(), false);
  }


  @GetMapping("/following")
  public List<UserDTO> getFollowing(@RequestAttribute("user") User loggedUser) {
    return userService.getUserFollowing(loggedUser.getId(), true);
  }

  @PostMapping("/updateFollow")
  public void updateUserFollow(@RequestAttribute("user") User loggedUser,
                               @RequestBody UserFollowDTO userFollowDTO) {
    userService.followUser(userFollowDTO);
  }


  @GetMapping("/users")
  public List<UserDTO> getAllUsers(@RequestAttribute("user") User loggedUser) {
    return userService.getAllUsers();
  }


  @GetMapping("/my-workouts")
  public List<Workout> getMyWorkouts(@RequestAttribute("user") User loggedUser) {
    return workoutService.getWorkoutsByUserId(loggedUser.getId());
  }


  @PostMapping("/request")
  public void requestWorkouts(@RequestAttribute("user") User loggedUser) {
    workoutService.createNewWorkoutRequest(loggedUser.getId());
  }


  @PostMapping("/update-profile")
  public ApiResponse authenticate(@RequestBody UserUpdateDTO userUpdateDTO) {

    try {
      userService.updateUser(userUpdateDTO);
    } catch (Exception e) {

      ApiResponse response = ApiResponse.builder()
              .code(400)
              .message(e.getMessage())
              .build();

      return response;

    }

    return ApiResponse.builder()
            .code(200)
            .message("Update Successful")
            .build();

  }

}
