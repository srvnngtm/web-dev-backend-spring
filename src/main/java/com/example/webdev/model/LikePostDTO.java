package com.example.webdev.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikePostDTO {

  private Integer postId;

  private Integer userId;

  private Boolean isLike;

}
