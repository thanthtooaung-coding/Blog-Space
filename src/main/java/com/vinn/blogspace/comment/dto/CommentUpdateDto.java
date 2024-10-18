package com.vinn.blogspace.comment.dto;

import com.vinn.blogspace.comment.enums.CommentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data Transfer Object to update a Comment.
 */
@Getter
@Setter
@ToString(exclude = {"content"})
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDto {

    @NotBlank(message = "Content is required")
    @Size(max = 250, message = "Title cannot exceed 250 characters")
    private String content;

    private CommentStatus status;
}
