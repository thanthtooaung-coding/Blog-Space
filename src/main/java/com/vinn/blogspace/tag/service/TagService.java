package com.vinn.blogspace.tag.service;

import com.vinn.blogspace.common.services.BaseService;
import com.vinn.blogspace.tag.dto.TagDto;
import com.vinn.blogspace.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService extends BaseService<Tag,Long> {
    TagDto getTagById(Long id);
    Page<TagDto> getAllTags(Pageable pageable);
    TagDto createTag(TagDto tagDto);
    TagDto updateTag(Long id, TagDto tagDto);
    void deleteTag(Long id);
}
