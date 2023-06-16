package com.example.webdev.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostUserLikeMap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int postId;

  private int userId;

  private boolean isLiked;

}
