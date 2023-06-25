package com.example.webdev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


  private String firstName;
  private String lastName;
  private String profilePicture;
  private int userId;

  private Double height;
  private Double weight;




}
