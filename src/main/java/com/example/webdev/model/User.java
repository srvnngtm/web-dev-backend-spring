package com.example.webdev.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
public class User {

  @Id
  private int id;
  private String username;
  private String password;
  private String role;

  private String firstName;
  private String lastName;

}
