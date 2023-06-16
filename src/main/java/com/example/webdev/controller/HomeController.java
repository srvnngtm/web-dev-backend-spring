package com.example.webdev.controller;

import com.example.webdev.model.BasicUserDetails;
import com.example.webdev.model.CreatePostDTO;
import com.example.webdev.model.FeedDTO;
import com.example.webdev.model.LikePostDTO;
import com.example.webdev.model.User;
import com.example.webdev.service.PostService;
import com.example.webdev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
//@CrossOrigin
public class HomeController {

  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;


  @GetMapping("")
  public String getHomeText(@RequestAttribute("user") User loggedUser){
    return "Welcome " + loggedUser.getUsername();
  }



  @GetMapping("/basic")
  public BasicUserDetails getBasicUserDetails(@RequestAttribute("user") User loggedUser){
    BasicUserDetails basicUserDetails = userService.getBasicUserDetailsByUserId(loggedUser.getId());
    return basicUserDetails;
  }


  @GetMapping("/feed")
  public FeedDTO getFeed(@RequestAttribute("user") User loggedUser){
    FeedDTO feed = postService.getFeed(loggedUser.getId());
    return feed;
  }


  @PostMapping("/createPost")
  public void createPost(@RequestAttribute("user") User loggedUser,
                         @RequestBody CreatePostDTO createPostDTO){

    postService.createPost(createPostDTO, loggedUser.getId());

  }


  @PostMapping("/updateLike")
  public void updatePostLike(@RequestAttribute("user") User loggedUser,
                       @RequestBody LikePostDTO likePostDTO){
//  likePostDTO.setPostId(loggedUser.getId());
  postService.updateLikeForPost(likePostDTO);

}










}
