package com.vinn.blogspace.post.platforms.github.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for a GitHub Blog Post.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepoDto {
	private String name;
	private String description;
	private String html_url;
	private String language;
	private int stargazers_count;
}
