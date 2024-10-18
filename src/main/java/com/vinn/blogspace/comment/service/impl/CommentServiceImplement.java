package com.vinn.blogspace.comment.service.impl;

import com.vinn.blogspace.comment.dto.CommentCreateDto;
import com.vinn.blogspace.comment.dto.CommentDto;
import com.vinn.blogspace.comment.dto.CommentUpdateDto;
import com.vinn.blogspace.comment.entity.Comment;
import com.vinn.blogspace.comment.repository.CommentRepository;
import com.vinn.blogspace.comment.service.CommentService;
import com.vinn.blogspace.common.services.impl.BaseServiceImpl;
import com.vinn.blogspace.post.user.service.PostService;
import com.vinn.blogspace.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImplement extends BaseServiceImpl<Comment, Long> implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final UserService userService;

    @Override
    protected JpaRepository<Comment, Long> getRepository() { return commentRepository; }

    @Override
    protected String getEntityName() { return "Comment"; }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = findById(id);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public Page<CommentDto> getAllComments(Pageable pageable) {
        return findAll(pageable).map(commentDto -> modelMapper.map(commentDto, CommentDto.class));
    }

    @Override
    public CommentDto createComment(CommentCreateDto commentDto) throws IllegalArgumentException {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setAuthor(userService.findById(commentDto.getAuthorId()));
        comment.setPost(postService.findById(commentDto.getPostId()));
        Comment savedComment = create(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(Long id, CommentUpdateDto commentDto) throws IllegalArgumentException {
        Comment comment = findById(id);
        comment.setContent(commentDto.getContent());
        comment.setStatus(commentDto.getStatus());
        Comment updatedComment = update(id, comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long id) { delete(id); }
}
