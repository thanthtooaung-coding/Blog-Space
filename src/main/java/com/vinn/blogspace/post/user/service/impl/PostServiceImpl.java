package com.vinn.blogspace.post.user.service.impl;

import com.vinn.blogspace.category.entity.Category;
import com.vinn.blogspace.post.user.dto.PostDto;
import com.vinn.blogspace.post.user.dto.PostCreateDto;
import com.vinn.blogspace.post.user.dto.PostUpdateDto;
import com.vinn.blogspace.common.exceptions.ResourceNotFoundException;
import com.vinn.blogspace.post.user.entity.Post;
import com.vinn.blogspace.post.user.enums.PostStatus;
import com.vinn.blogspace.post.user.repository.PostRepository;
import com.vinn.blogspace.category.repository.CategoryRepository;
import com.vinn.blogspace.tag.repository.TagRepository;
import com.vinn.blogspace.tag.entity.Tag;
import com.vinn.blogspace.user.entity.User;
import com.vinn.blogspace.user.repository.UserRepository;
import com.vinn.blogspace.post.user.service.PostService;
import com.vinn.blogspace.common.services.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl extends BaseServiceImpl<Post, Long> implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    protected PostRepository getRepository() {
        return postRepository;
    }

    @Override
    protected String getEntityName() {
        return "Post";
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = findById(id);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return findAll(pageable).map(post -> modelMapper.map(post, PostDto.class));
    }

    @Override
    @Transactional
    public PostDto createPost(PostCreateDto postCreateDto) {
        Post post = modelMapper.map(postCreateDto, Post.class);
        User author = userRepository.findById(postCreateDto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", postCreateDto.getAuthorId()));
        post.setAuthor(author);
        setCategories(post, postCreateDto.getCategoryIds());
        setTags(post, postCreateDto.getTagIds());
        Post savedPost = create(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    @Transactional
    public PostDto updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post existingPost = findById(id);
        modelMapper.map(postUpdateDto, existingPost);
        setCategories(existingPost, postUpdateDto.getCategoryIds());
        setTags(existingPost, postUpdateDto.getTagIds());
        Post updatedPost = update(id, existingPost);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public Page<PostDto> searchPosts(String title, List<Long> categoryIds, List<Long> tagIds, Pageable pageable) {
        Specification<Post> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.join("categories").get("id").in(categoryIds));
        }

        if (tagIds != null && !tagIds.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.join("tags").get("id").in(tagIds));
        }

        return postRepository.findAll(spec, pageable)
                .map(post -> modelMapper.map(post, PostDto.class));
    }

    @Override
    @Transactional
    public PostDto updatePostStatus(Long id, String status) throws IllegalArgumentException {
        Post post = findById(id);
        post.setStatus(PostStatus.valueOf(status.toUpperCase()));
        Post updatedPost = update(id, post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        delete(id);
    }

    private void setCategories(Post post, List<Long> categoryIds) {
        if (categoryIds != null) {
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            post.setCategories(categories);
        }
    }

    private void setTags(Post post, List<Long> tagIds) {
        if (tagIds != null) {
            List<Tag> tags = tagRepository.findAllById(tagIds);
            post.setTags(tags);
        }
    }
}