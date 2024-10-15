package com.vinn.blogspace.post.platforms.facebook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacebookPostDto {
	private String id;
	private String message;
	private String created_time;
	private String story;
}