package com.dave.spring.alpha.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dave.spring.alpha.model.Workout;

public interface WorkoutRepo extends JpaRepository<Workout, Integer> {
	
	@Query("SELECT w FROM Workout w WHERE w.user = ?#{authentication.name}")
	List<Workout> findAllByUser();
}
