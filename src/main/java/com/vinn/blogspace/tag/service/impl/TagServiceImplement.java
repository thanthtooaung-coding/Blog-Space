package com.vinn.blogspace.tag.service.impl;

import com.vinn.blogspace.common.exceptions.EntityDeletionException;
import com.vinn.blogspace.common.services.impl.BaseServiceImpl;
import com.vinn.blogspace.tag.dto.TagDto;
import com.vinn.blogspace.tag.entity.Tag;
import com.vinn.blogspace.tag.repository.TagRepository;
import com.vinn.blogspace.tag.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagServiceImplement extends BaseServiceImpl<Tag, Long> implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    protected JpaRepository<Tag, Long> getRepository() { return tagRepository; }

    @Override
    protected String getEntityName() { return "Tag"; }

    @Override
    public TagDto getTagById(Long id) {
        Tag tag = findById(id);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public Page<TagDto> getAllTags(Pageable pageable) {
        return findAll(pageable).map(tag -> modelMapper.map(tag, TagDto.class));
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Tag savedTag = create(tag);
        return modelMapper.map(savedTag, TagDto.class);
    }

    @Override
    public TagDto updateTag(Long id, TagDto tagDto) {
        Tag tag = findById(id);
        tag.setName(tagDto.getName());
        Tag updatedTag = update(id, tag);
        return modelMapper.map(updatedTag, TagDto.class);
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = findById(id);
        if (!tag.getPosts().isEmpty()) {
            throw new EntityDeletionException(getEntityName(), "related records");
        }
        delete(id);
    }
}
