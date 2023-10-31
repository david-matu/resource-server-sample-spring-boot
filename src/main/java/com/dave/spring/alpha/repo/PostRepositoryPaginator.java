package com.dave.spring.alpha.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dave.spring.alpha.model.Post;

public interface PostRepositoryPaginator extends PagingAndSortingRepository<Post, Long> {

}
