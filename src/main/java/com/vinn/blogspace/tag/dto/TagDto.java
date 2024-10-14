package com.vinn.blogspace.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Tag.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long id; // Unique identifier for the tag

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    private String name;
}
