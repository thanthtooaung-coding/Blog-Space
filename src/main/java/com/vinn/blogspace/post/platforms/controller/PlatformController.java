package com.vinn.blogspace.post.platforms.controller;

import java.util.stream.Collectors;

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

	@GetMapping("github/{username}")
	public ResponseEntity<PagedModel<EntityModel<GitHubRepoDto>>> getReposByUsername(@PathVariable String username,
			Pageable pageable) throws Exception {

		Page<GitHubRepoDto> repos = gitHubService.getReposByUsername(username, pageable);
		PagedModel<EntityModel<GitHubRepoDto>> pagedModel = PagedModel.of(
				repos.getContent().stream().map(repo -> EntityModel.of(repo)).collect(Collectors.toList()),
				new PagedModel.PageMetadata(repos.getSize(), repos.getNumber(), repos.getTotalElements(),
						repos.getTotalPages()));
		return ResponseEntity.ok(pagedModel);
	}

	@GetMapping("facebook/{username}")
	public ResponseEntity<PagedModel<EntityModel<FacebookPostDto>>> getPostsByUsername(@PathVariable String username,
			Pageable pageable) throws Exception {

		Page<FacebookPostDto> posts = facebookService.getPostsByUsername(username, pageable);
		PagedModel<EntityModel<FacebookPostDto>> pagedModel = PagedModel.of(
				posts.getContent().stream().map(post -> EntityModel.of(post)).collect(Collectors.toList()),
				new PagedModel.PageMetadata(posts.getSize(), posts.getNumber(), posts.getTotalElements(),
						posts.getTotalPages()));
		return ResponseEntity.ok(pagedModel);
	}
}
