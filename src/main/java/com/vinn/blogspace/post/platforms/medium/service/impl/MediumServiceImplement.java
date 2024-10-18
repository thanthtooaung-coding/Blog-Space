package com.vinn.blogspace.post.platforms.medium.service.impl;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.vinn.blogspace.post.platforms.medium.dto.MediumPostDto;
import com.vinn.blogspace.post.platforms.medium.service.MediumService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediumServiceImplement implements MediumService {

    @Override
    public Page<MediumPostDto> getPostsByUsername(String username, Pageable pageable) throws IOException, FeedException, URISyntaxException {
        String url = "https://medium.com/feed/@" + username;
        URI feedUri = new URI(url);

        try (XmlReader reader = new XmlReader(feedUri.toURL().openStream())) {
            SyndFeed feed = new SyndFeedInput().build(reader);

            List<MediumPostDto> allPosts = feed.getEntries().stream()
                    .map(entry ->
                            new MediumPostDto(
                                    entry.getTitle(),
                                    entry.getUri(),
                                    entry.getPublishedDate()
                            ))
                    .collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), allPosts.size());
            List<MediumPostDto> paginatedPosts = allPosts.subList(start, end);

            return new PageImpl<>(paginatedPosts, pageable, allPosts.size());
        }
    }
}
