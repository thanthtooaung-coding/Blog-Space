package com.vinn.blogspace.post.platforms.youtube.service;

import com.vinn.blogspace.post.platforms.youtube.dto.YouTubeVideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface YouTubeService {
    Page<YouTubeVideoDto> getVideosByChannelId(String channelId, Pageable pageable) throws Exception;
}
