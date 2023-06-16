package com.example.webdev.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {

  private String postBody;

  private String postTitle;

  private String imageUrl;

}
