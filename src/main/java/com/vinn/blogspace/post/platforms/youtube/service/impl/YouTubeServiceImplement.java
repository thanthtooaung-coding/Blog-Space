package com.vinn.blogspace.post.platforms.youtube.service.impl;

import com.vinn.blogspace.common.exceptions.ResourceNotFoundException;
import com.vinn.blogspace.common.utils.ApiKeys;
import com.vinn.blogspace.post.platforms.youtube.dto.YouTubeVideoDto;
import com.vinn.blogspace.post.platforms.youtube.service.YouTubeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class YouTubeServiceImplement implements YouTubeService {

    private final ModelMapper modelMapper;

    private final ApiKeys apiKey;

    @Override
    public Page<YouTubeVideoDto> getVideosByChannelId(String channelId, Pageable pageable) {
        final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId={channelId}&maxResults={maxResults}&pageToken={pageToken}&key={apiKey}";

        RestTemplate restTemplate = new RestTemplate();

        String pageToken = pageable.getPageNumber() == 0 ? null : pageable.getPageNumber() > 1 ? pageable.getPageNumber() - 1 + "" : "";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                YOUTUBE_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                channelId,
                pageable.getPageSize(),
                pageToken,
                apiKey.getYouTubeApiKey()
        );

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        if (items == null || items.isEmpty()) {
            throw new ResourceNotFoundException("No videos found for channel ID: " + channelId, "channelId", items);
        }

        List<YouTubeVideoDto> videoDtos = items.stream()
                .filter(item -> {
                    Map<String, String> idMap = (Map<String, String>) item.get("id");
                    return "youtube#video".equals(idMap.get("kind"));
                })
                .map(item -> {
                    YouTubeVideoDto dto = modelMapper.map(item.get("snippet"), YouTubeVideoDto.class);

                    Map<String, String> idMap = (Map<String, String>) item.get("id");
                    String videoId = idMap.get("videoId");

                    if (videoId != null) {
                        dto.setThumbnailUrl("https://www.youtube.com/watch?v=" + videoId);
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), videoDtos.size());
        List<YouTubeVideoDto> paginatedList = videoDtos.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, items.size());
    }

}