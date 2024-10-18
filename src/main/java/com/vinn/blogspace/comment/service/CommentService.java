package com.vinn.blogspace.comment.service;

import com.vinn.blogspace.comment.dto.CommentCreateDto;
import com.vinn.blogspace.comment.dto.CommentDto;
import com.vinn.blogspace.comment.dto.CommentUpdateDto;
import com.vinn.blogspace.comment.entity.Comment;
import com.vinn.blogspace.common.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService extends BaseService<Comment, Long> {
    CommentDto getCommentById(Long id);
    Page<CommentDto> getAllComments(Pageable pageable);
    CommentDto createComment(CommentCreateDto commentDto);
    CommentDto updateComment(Long id, CommentUpdateDto commentDto);
    void deleteComment(Long id);
}
