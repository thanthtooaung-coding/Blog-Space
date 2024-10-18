package com.vinn.blogspace.post.platforms.medium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Data Transfer Object for a Medium Blog Post.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediumPostDto {
    private String title;
    private String url;
    private Date publishedDate;
}
