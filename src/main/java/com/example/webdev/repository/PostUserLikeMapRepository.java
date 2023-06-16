package com.example.webdev.repository;

import com.example.webdev.model.PostUserLikeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface PostUserLikeMapRepository extends JpaRepository<PostUserLikeMap, Integer> {
  Optional<PostUserLikeMap> findByUserIdAndPostId(Integer userId, Integer postId);

}
