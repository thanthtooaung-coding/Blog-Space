package com.vinn.blogspace.post.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"content"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id; // Unique identifier for the tag

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private Long authorId;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String status;

    private List<Long> commentIds;
    private List<Long> categoryIds;
    private List<Long> tagIds;
}