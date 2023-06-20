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
  // a follows b
  private Integer userId;   // current user (a)

  private Integer followUserId;    // the user who the user id is following.(b)

  private Boolean isFollowing ;

}
