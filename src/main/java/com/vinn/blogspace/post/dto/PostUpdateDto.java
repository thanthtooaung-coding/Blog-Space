package com.vinn.blogspace.post.dto;

import com.vinn.blogspace.post.enums.PostStatus;
import lombok.*;
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

    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    private String content;

    private PostStatus status;

    private List<Long> categoryIds;

    private List<Long> tagIds;
}