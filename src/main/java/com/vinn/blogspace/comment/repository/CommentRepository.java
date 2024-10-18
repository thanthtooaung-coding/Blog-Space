package com.vinn.blogspace.comment.repository;

import com.vinn.blogspace.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
