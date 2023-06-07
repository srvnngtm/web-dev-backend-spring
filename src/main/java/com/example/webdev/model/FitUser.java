package com.example.webdev.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitUser {

  @Id
  private int id;


  private int userId;
  private double height;
  private double weight;
  private String profilePicture;

}
