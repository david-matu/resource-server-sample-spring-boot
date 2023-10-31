package com.dave.spring.alpha;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dave.spring.alpha.repo.PostRepository;
//import com.dave.spring.alpha.user.UserRepository;

//import lombok.RequiredArgsConstructor;

/**
 * @author David
 * 20/8/2023
 * 
 * 	Generate Unique ID by matching a random against the existing entries in the database
 */

@Component
public class IDGen {
	
	//@Autowired
	private final PostRepository postRepo;
	
	//private final UserRepository userRepo;
	
	public IDGen(PostRepository postRepo) {	//UserRepository userRepo
		this.postRepo = postRepo;
		//this.userRepo = userRepo;
	}
	
	/*
	public static boolean isPostIDExists(long postID) {
		return this.postRepo.existsById(postID);
	}
	*/
	
	public long generatePostID() {
		long id = new Random().nextLong();
		
		boolean idExists = postRepo.existsById(id);
		
		if(!idExists) {
			return id;
		} else {
			generatePostID();
		}
		
		return id;
	}
	
	/*
	public long generateUserID() {
		long id = new Random().nextLong();
		
		boolean idExists = userRepo.existsById(id);
		
		if(!idExists) {
			return id;
		} else {
			generatePostID();
		}
		
		return id;
	}
	*/
	
	/*
	public boolean isUniqueUsername(String username) {
		
		boolean idExists = userRepo.findByUsername(username).equals(null);	//For post review
		
		if(!idExists) {
			return true;
		}
		
		return false;
	}
	*/
}
