package com.example.webdev.repository;

import com.example.webdev.model.UserUserFollowMap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserUserFollowMapRepository extends JpaRepository<UserUserFollowMap, Integer> {

  List<UserUserFollowMap> findAllByUserId(Integer userId);


  List<UserUserFollowMap> findAllByFollowUserId(Integer followUserId);

  Optional<UserUserFollowMap> findByUserIdAndAndFollowUserId(Integer userId, Integer followUserId);


}
