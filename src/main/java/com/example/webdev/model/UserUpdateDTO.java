package com.example.webdev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

  private int userId;

  private String firstName;

  private String lastName;

  private Integer height;

  private Integer weight;

}
