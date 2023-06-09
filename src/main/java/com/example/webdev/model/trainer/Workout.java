package com.example.webdev.model.trainer;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private Integer userId;

  private String name;

  private String instructions;

  private Integer sets;

  private Integer reps;

}
