package com.example.webdev.repository;

import com.example.webdev.model.trainer.WorkoutRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutRequestRepository extends JpaRepository<WorkoutRequest, Integer> {

  Optional<WorkoutRequest> findByUserId(Integer userId);
}
