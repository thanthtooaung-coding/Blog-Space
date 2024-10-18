package com.vinn.blogspace.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for User.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id; // Unique identifier for the user

    private String username;

    private String email;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String role;

    private List<Long> postIds;
}
