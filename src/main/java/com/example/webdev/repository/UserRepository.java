package com.example.webdev.repository;

import com.example.webdev.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  List<User> findAllByRole(String role);

}
