package com.example.webdev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {

  private Integer userId;

  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private String role;

  // fit user specific, can  be null

  private Double height;
  private Double weight;
  private String profilePicture;


}
