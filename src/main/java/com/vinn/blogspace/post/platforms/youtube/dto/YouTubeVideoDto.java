package com.vinn.blogspace.post.platforms.youtube.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for a YouTube Video.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YouTubeVideoDto {
    private String title;
    private String description;
    private String publishedAt;
    private String thumbnailUrl;
}