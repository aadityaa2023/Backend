package com.example.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.blog.dto.CreatePostDto;
import com.example.blog.dto.PostDto;

/**
 * Service interface for post operations.
 */
public interface PostService {
    Page<PostDto> listPosts(Pageable pageable);
    PostDto getPostById(Long id);
    PostDto createPost(CreatePostDto createPostDto);
    PostDto updatePost(Long id, CreatePostDto createPostDto);
    void deletePost(Long id);
}
