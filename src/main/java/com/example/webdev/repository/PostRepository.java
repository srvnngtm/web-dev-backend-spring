package com.example.webdev.repository;

import com.example.webdev.model.Post;
import com.example.webdev.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  void deleteAllByPostUserId(Integer userId);


}
