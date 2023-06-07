package com.example.webdev.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FitUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


  private int userId;
  private double height;
  private double weight;
  private String profilePicture;

}
