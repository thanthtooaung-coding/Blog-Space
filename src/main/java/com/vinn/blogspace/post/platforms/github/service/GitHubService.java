package com.vinn.blogspace.post.platforms.github.service;

import com.vinn.blogspace.post.platforms.github.dto.GitHubRepoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GitHubService {
	Page<GitHubRepoDto> getReposByUsername(String username, Pageable pageable) throws Exception;
}
