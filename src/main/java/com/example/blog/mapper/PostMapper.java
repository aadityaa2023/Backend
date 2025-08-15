package com.example.blog.mapper;

import com.example.blog.dto.CreatePostDto;
import com.example.blog.dto.PostDto;
import com.example.blog.model.Post;

/**
 * Simple mapper utility to convert between Post and DTOs.
 * Kept static for clarity; swap to MapStruct if preferred.
 */
public class PostMapper {

    private PostMapper() {}

    public static PostDto toDto(Post post) {
        if (post == null) return null;
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    public static Post toEntity(CreatePostDto dto) {
        if (dto == null) return null;
        Post p = new Post();
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        p.setAuthor(dto.getAuthor());
        return p;
    }

    public static void updateEntityFromDto(Post post, CreatePostDto dto) {
        // Update mutable fields only
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(dto.getAuthor());
    }
}
