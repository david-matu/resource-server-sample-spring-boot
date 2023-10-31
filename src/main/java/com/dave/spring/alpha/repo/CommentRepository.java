package com.dave.spring.alpha.repo;

import org.springframework.data.repository.CrudRepository;

import com.dave.spring.alpha.model.Comment;


public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
}