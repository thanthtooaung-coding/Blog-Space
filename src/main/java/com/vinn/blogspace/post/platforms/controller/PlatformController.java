package com.vinn.blogspace.post.platforms.controller;

import java.util.stream.Collectors;

import com.vinn.blogspace.post.platforms.medium.dto.MediumPostDto;
import com.vinn.blogspace.post.platforms.medium.service.MediumService;
import com.vinn.blogspace.post.platforms.youtube.dto.YouTubeVideoDto;
import com.vinn.blogspace.post.platforms.youtube.service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinn.blogspace.post.platforms.facebook.dto.FacebookPostDto;
import com.vinn.blogspace.post.platforms.facebook.service.FacebookService;
import com.vinn.blogspace.post.platforms.github.dto.GitHubRepoDto;
import com.vinn.blogspace.post.platforms.github.service.GitHubService;

@RestController
@RequestMapping("/api/posts/platforms")
public class PlatformController {

	@Autowired
	private GitHubService gitHubService;

	@Autowired
	private FacebookService facebookService;

	@Autowired
	private MediumService mediumService;

	@Autowired
	private YouTubeService youTubeService;

	@GetMapping("/github/{username}")
	public ResponseEntity<PagedModel<EntityModel<GitHubRepoDto>>> getReposByUsername(@PathVariable String username,
																					 Pageable pageable) throws Exception {

		Page<GitHubRepoDto> repos = gitHubService.getReposByUsername(username, pageable);
		PagedModel<EntityModel<GitHubRepoDto>> pagedModel = PagedModel.of(
				repos.getContent().stream().map(EntityModel::of).collect(Collectors.toList()),
				new PagedModel.PageMetadata(repos.getSize(), repos.getNumber(), repos.getTotalElements(),
						repos.getTotalPages()));
		return ResponseEntity.ok(pagedModel);
	}

	@GetMapping("/facebook/{username}")
	public ResponseEntity<PagedModel<EntityModel<FacebookPostDto>>> getPostsByUsername(@PathVariable String username,
																					   Pageable pageable) throws Exception {

		Page<FacebookPostDto> posts = facebookService.getPostsByUsername(username, pageable);
		PagedModel<EntityModel<FacebookPostDto>> pagedModel = PagedModel.of(
				posts.getContent().stream().map(EntityModel::of).collect(Collectors.toList()),
				new PagedModel.PageMetadata(posts.getSize(), posts.getNumber(), posts.getTotalElements(),
						posts.getTotalPages()));
		return ResponseEntity.ok(pagedModel);
	}

	@GetMapping("/medium/{username}")
	public ResponseEntity<PagedModel<EntityModel<MediumPostDto>>> getMediumPostsByUsername(
			@PathVariable String username,
			Pageable pageable) throws Exception {

		Page<MediumPostDto> postsPage = mediumService.getPostsByUsername(username, pageable);

		PagedModel<EntityModel<MediumPostDto>> pagedModel = PagedModel.of(
				postsPage.getContent().stream().map(EntityModel::of).collect(Collectors.toList()),
				new PagedModel.PageMetadata(
						postsPage.getSize(),
						postsPage.getNumber(),
						postsPage.getTotalElements(),
						postsPage.getTotalPages())
		);

		return ResponseEntity.ok(pagedModel);
	}

	@GetMapping("/youtube/channel/{channelId}/videos")
	public ResponseEntity<PagedModel<EntityModel<YouTubeVideoDto>>> getVideosByChannelId(@PathVariable String channelId,
																						 Pageable pageable) throws Exception {
		Page<YouTubeVideoDto> videos = youTubeService.getVideosByChannelId(channelId, pageable);
		PagedModel<EntityModel<YouTubeVideoDto>> pagedModel = PagedModel.of(
				videos.getContent().stream().map(EntityModel::of).collect(Collectors.toList()),
				new PagedModel.PageMetadata(videos.getSize(), videos.getNumber(), videos.getTotalElements(),
						videos.getTotalPages()));
		return ResponseEntity.ok(pagedModel);
	}

}