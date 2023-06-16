package com.example.webdev.repository;

import com.example.webdev.model.FitUser;
import com.example.webdev.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

@Repository
public interface FitUserRepository extends JpaRepository<FitUser, Integer> {

  FitUser getFitUsersByUserId(Integer userId);

  List<FitUser> findAllByUserIdIn(List<Integer> userIds);

}
