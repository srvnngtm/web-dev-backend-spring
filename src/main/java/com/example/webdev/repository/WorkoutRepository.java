package com.example.webdev.repository;

import com.example.webdev.model.trainer.Workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

  List<Workout> findAllByUserId(Integer userId);

}
