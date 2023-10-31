package com.dave.spring.alpha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dave.spring.alpha.model.Workout;
import com.dave.spring.alpha.repo.WorkoutRepo;

@Service
public class WorkoutService {
	
	@Autowired
	private WorkoutRepo workoutRepo;
	
	@PreAuthorize("#workout.user == authentication.name")
	public void saveWorkout(Workout workout) {
		workoutRepo.save(workout);
	}
	
	public List<Workout> findWorkouts() {
		return workoutRepo.findAllByUser();
	}
	
	/*
	 * Authorization for Delete will be applied at the endpoint layer 
	 */
	public void deleteWorkout(Integer id) {
		workoutRepo.deleteById(id);
	}
}
