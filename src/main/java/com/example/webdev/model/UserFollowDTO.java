package com.example.webdev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowDTO {

  private Integer userId;   // current user

  private Integer followUserId;    // the user who is the user id is following.

  private Boolean isFollowing ;

}
