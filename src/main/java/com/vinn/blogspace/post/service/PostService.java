package com.vinn.blogspace.post.service;

import com.vinn.blogspace.post.dto.PostDto;
import com.vinn.blogspace.post.dto.PostCreateDto;
import com.vinn.blogspace.post.dto.PostUpdateDto;
import com.vinn.blogspace.post.entity.Post;
import com.vinn.blogspace.common.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService extends BaseService<Post, Long> {
    PostDto getPostById(Long id);
    Page<PostDto> getAllPosts(Pageable pageable);
    PostDto createPost(PostCreateDto postCreateDto);
    PostDto updatePost(Long id, PostUpdateDto postUpdateDto);
    Page<PostDto> searchPosts(String title, List<Long> categoryIds, List<Long> tagIds, Pageable pageable);
    PostDto updatePostStatus(Long id, String status);

    @Transactional
    void deletePost(Long id);
}