package com.vinn.blogspace.post.platforms.github.service.impl;

import com.vinn.blogspace.post.platforms.github.dto.GitHubRepoDto;
import com.vinn.blogspace.post.platforms.github.service.GitHubService;
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
public class GitHubServiceImplement implements GitHubService {

	private final String GITHUB_API_URL = "https://api.github.com/users/";

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<GitHubRepoDto> getReposByUsername(String username, Pageable pageable) throws Exception {
		String url = GITHUB_API_URL + username + "/repos";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object>[] repos = restTemplate.getForObject(url, Map[].class);

		if (repos == null || repos.length == 0) {
			throw new Exception("No repositories found for user: " + username);
		}

		List<GitHubRepoDto> repoDtos = Arrays.stream(repos).map(repo -> modelMapper.map(repo, GitHubRepoDto.class))
				.collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), repoDtos.size());
		List<GitHubRepoDto> paginatedList = repoDtos.subList(start, end);

		return new PageImpl<>(paginatedList, pageable, repoDtos.size());
	}
}
