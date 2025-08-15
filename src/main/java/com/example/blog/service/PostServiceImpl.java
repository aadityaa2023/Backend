package com.example.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.blog.dto.CreatePostDto;
import com.example.blog.dto.PostDto;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.PostMapper;
import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;

import jakarta.transaction.Transactional;

/**
 * Implementation of the PostService interface.
 * Handles business logic for managing blog posts.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(final PostRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a paginated list of posts.
     *
     * @param pageable the pagination and sorting information
     * @return a page of PostDto objects
     */
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Page<PostDto> listPosts(Pageable pageable) {
        return repository.findAll(pageable).map(PostMapper::toDto);
    }

    /**
     * Retrieves a single post by its ID.
     *
     * @param id the ID of the post
     * @return the PostDto object
     * @throws ResourceNotFoundException if no post is found with the given ID
     */
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PostDto getPostById(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return PostMapper.toDto(post);
    }

    /**
     * Creates a new post.
     *
     * @param createPostDto the DTO containing post data
     * @return the saved PostDto object
     */
    @Override
    public PostDto createPost(CreatePostDto createPostDto) {
        Post post = PostMapper.toEntity(createPostDto);
        Post saved = repository.save(post);
        return PostMapper.toDto(saved);
    }

    /**
     * Updates an existing post.
     *
     * @param id the ID of the post to update
     * @param createPostDto the DTO containing updated post data
     * @return the updated PostDto object
     * @throws ResourceNotFoundException if the post does not exist
     */
    @Override
    public PostDto updatePost(Long id, CreatePostDto createPostDto) {
        Post existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        PostMapper.updateEntityFromDto(existing, createPostDto);
        Post saved = repository.save(existing);
        return PostMapper.toDto(saved);
    }

    /**
     * Deletes a post by its ID.
     *
     * @param id the ID of the post to delete
     * @throws ResourceNotFoundException if the post does not exist
     */
    @Override
    public void deletePost(Long id) {
        Post existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        repository.delete(existing);
    }
}
