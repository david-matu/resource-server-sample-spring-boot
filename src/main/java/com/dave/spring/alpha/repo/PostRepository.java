package com.dave.spring.alpha.repo;

import org.springframework.data.repository.CrudRepository;

import com.dave.spring.alpha.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
