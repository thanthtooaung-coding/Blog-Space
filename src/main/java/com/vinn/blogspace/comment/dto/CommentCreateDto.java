package com.vinn.blogspace.comment.dto;

import com.vinn.blogspace.comment.enums.CommentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data Transfer Object to create a Comment.
 */
@Getter
@Setter
@ToString(exclude = {"content"})
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    private Long id; // Unique identifier for the comment

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotBlank(message = "Content is required")
    @Size(max = 250, message = "Title cannot exceed 250 characters")
    private String content;

    private CommentStatus status = CommentStatus.PENDING;
}
