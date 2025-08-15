package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.Post;

/**
 * Basic JPA repository. Paging is supported via JpaRepository.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
