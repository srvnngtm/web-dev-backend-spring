package com.example.webdev.service;

import com.example.webdev.model.CreatePostDTO;
import com.example.webdev.model.FeedDTO;
import com.example.webdev.model.FeedPostDTO;
import com.example.webdev.model.LikePostDTO;
import com.example.webdev.model.Post;
import com.example.webdev.model.PostUserLikeMap;
import com.example.webdev.model.User;
import com.example.webdev.repository.PostRepository;
import com.example.webdev.repository.PostUserLikeMapRepository;
import com.example.webdev.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostUserLikeMapRepository postUserLikeMapRepository;

  @Autowired
  private UserRepository userRepository;



  public void createPost(CreatePostDTO createPostDTO, Integer userId){
    Post post = Post.builder()
            .postUserId(userId)
            .postTitle(createPostDTO.getPostTitle())
            .postBody(createPostDTO.getPostBody())
            .imageUrl(createPostDTO.getImageUrl())
            .build();

    postRepository.save(post);

  }

  public void updateLikeForPost(LikePostDTO likePostDTO){

    Optional<PostUserLikeMap> byUserIdAndPostId = postUserLikeMapRepository.findByUserIdAndPostId(likePostDTO.getUserId(),
            likePostDTO.getPostId());

    if(byUserIdAndPostId.isPresent()){
      PostUserLikeMap postUserLikeMap = byUserIdAndPostId.get();
      postUserLikeMap.setLiked(likePostDTO.getIsLike());
      postUserLikeMapRepository.save(postUserLikeMap);
    }else{

      PostUserLikeMap postUserLikeMap = PostUserLikeMap.builder()
              .userId(likePostDTO.getUserId())
              .postId(likePostDTO.getPostId())
              .isLiked(likePostDTO.getIsLike())
              .build();

      postUserLikeMapRepository.save(postUserLikeMap);
    }


  }






  public FeedDTO getFeed(Integer userId){

    // get all posts
    List<Post> postList = postRepository.findAll();
    List<PostUserLikeMap> likeMap = postUserLikeMapRepository.findAll();


    //map likes by post id
    Map<Integer, List<PostUserLikeMap>> postIdToLikesMap = likeMap.stream()
                    .filter(PostUserLikeMap::isLiked)
                    .collect(Collectors.groupingBy(PostUserLikeMap::getPostId));

    //get posted user for all posts
    List<User> userList = userRepository.findAll();
    Map<Integer, String> userIdToUserNameMap = userList.stream()
            .collect(Collectors.toMap(User::getId, User::getUsername ));


    List<FeedPostDTO> feedPostDTOList = new ArrayList<>();


    for (Post post : postList) {

      List<PostUserLikeMap> eachPostLikes
              = postIdToLikesMap.getOrDefault(post.getId(),
              new ArrayList<>());

      PostUserLikeMap postUserLikeMap = eachPostLikes.stream()
              .filter(e -> e.getUserId() == userId)
              .findFirst().orElse(null);


      FeedPostDTO feedPostDTO = FeedPostDTO.builder()
              .postId(post.getId())
              .postBody(post.getPostBody())
              .postTitle(post.getPostTitle())
              .imageUrl(post.getImageUrl())
              .postUserName(userIdToUserNameMap.getOrDefault(post.getPostUserId(), "NO NAME " +
                      "FOUND"))
              .upvoteCount(eachPostLikes.size())
              .isUpvoteByCurrentUser(!Objects.isNull(postUserLikeMap))
              .build();

      feedPostDTOList.add(feedPostDTO);


    }

    Collections.reverse(feedPostDTOList);
    FeedDTO feedDTO = FeedDTO.builder().postList(feedPostDTOList).build();
    return feedDTO;
  }


}
