package com.vinn.blogspace.common.utils;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApiKeys {
    @Value("${youtube.api.key}")
    private String youTubeApiKey;
}
