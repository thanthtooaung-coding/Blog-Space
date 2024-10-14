package com.vinn.blogspace.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Comment.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id; // Unique identifier for the comment

    private Long postId;

    private Long authorId;

    @NotBlank(message = "Content is required")
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String status;
}
