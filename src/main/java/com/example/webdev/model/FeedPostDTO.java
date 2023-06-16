package com.example.webdev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostDTO {

   private int postId;

   private String postBody;

   private String postTitle;

   private String imageUrl;

   private String postUserName;

   private boolean isUpvoteByCurrentUser;

   private int upvoteCount;

}
