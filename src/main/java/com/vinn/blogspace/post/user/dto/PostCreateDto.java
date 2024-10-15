package com.vinn.blogspace.post.user.dto;

import com.vinn.blogspace.post.user.enums.PostStatus;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Data Transfer Object to create a Blog Post.
 */
@Getter
@Setter
@ToString(exclude = {"content"})
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {
	
	private Long id; // Unique identifier for the blog post

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    private PostStatus status = PostStatus.DRAFT;

    private List<Long> categoryIds;

    private List<Long> tagIds;
}