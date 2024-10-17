package com.vinn.blogspace.post.platforms.facebook.service;

import com.vinn.blogspace.post.platforms.facebook.dto.FacebookPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacebookService {
	Page<FacebookPostDto> getPostsByUsername(String username, Pageable pageable) throws Exception;
}