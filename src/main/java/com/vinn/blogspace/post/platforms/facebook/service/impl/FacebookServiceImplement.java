package com.vinn.blogspace.post.platforms.facebook.service.impl;

import com.vinn.blogspace.post.platforms.facebook.dto.FacebookPostDto;
import com.vinn.blogspace.post.platforms.facebook.service.FacebookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacebookServiceImplement implements FacebookService {

	private final String FACEBOOK_API_URL = "https://graph.facebook.com/";

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<FacebookPostDto> getPostsByUsername(String username, Pageable pageable) throws Exception {
		String url = FACEBOOK_API_URL + username + "/posts?access_token={ACCESS_TOKEN}";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object>[] posts = restTemplate.getForObject(url, Map[].class);

		if (posts == null || posts.length == 0) {
			throw new Exception("No posts found for user: " + username);
		}

		List<FacebookPostDto> postDtos = Arrays.stream(posts).map(post -> modelMapper.map(post, FacebookPostDto.class))
				.collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), postDtos.size());
		List<FacebookPostDto> paginatedList = postDtos.subList(start, end);

		return new PageImpl<>(paginatedList, pageable, postDtos.size());
	}
}