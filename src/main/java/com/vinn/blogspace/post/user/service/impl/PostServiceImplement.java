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
import com.vinn.blogspace.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImplement extends BaseServiceImpl<Post, Long> implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

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
        return convertToPostDto(post);
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return findAll(pageable).map(this::convertToPostDto);
    }

    private PostDto convertToPostDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        List<Long> tagIds = post.getTags().stream().map(Tag::getId).collect(Collectors.toList());
        postDto.setTagIds(tagIds);
        return postDto;
    }

    @Override
    @Transactional
    public PostDto createPost(PostCreateDto postCreateDto) {
        Post post = modelMapper.map(postCreateDto, Post.class);
        User author = userService.findById(postCreateDto.getAuthorId());
        post.setAuthor(author);
        setCategories(post, postCreateDto.getCategoryIds());
        setTags(post, postCreateDto.getTags());
        Post savedPost = create(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    @Transactional
    public PostDto updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post existingPost = findById(id);
        modelMapper.map(postUpdateDto, existingPost);
        setCategories(existingPost, postUpdateDto.getCategoryIds());
        setTags(existingPost, postUpdateDto.getTags());
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

    private void setTags(Post post, List<String> tagNames) {
        if (tagNames != null) {
            List<Tag> tags = tagNames.stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> {
                                Tag newTag = new Tag();
                                newTag.setName(tagName);
                                return tagRepository.save(newTag);
                            })).collect(Collectors.toList());

            post.setTags(tags);
        }
    }

    @Override
    public Page<PostDto> getPostsByUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Page<Post> posts = postRepository.findByAuthor(user, pageable);
        return posts.map(post -> modelMapper.map(post, PostDto.class));
    }
}