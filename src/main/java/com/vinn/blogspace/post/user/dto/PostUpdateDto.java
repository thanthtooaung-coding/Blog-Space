package com.vinn.blogspace.post.user.dto;

import com.vinn.blogspace.post.user.enums.PostStatus;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Data Transfer Object to update a Blog Post.
 */
@Getter
@Setter
@ToString(exclude = {"content"})
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {

	@NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

	@NotBlank(message = "Content is required")
    private String content;

    private PostStatus status;

    private List<Long> categoryIds;

    private List<String> tags;
}