package com.vinn.blogspace.post.platforms.medium.service;

import com.rometools.rome.io.FeedException;
import com.vinn.blogspace.post.platforms.medium.dto.MediumPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface MediumService {
    Page<MediumPostDto> getPostsByUsername(String username, Pageable pageable) throws IOException, FeedException, URISyntaxException;
}
