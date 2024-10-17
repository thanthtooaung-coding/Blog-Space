package com.vinn.blogspace.post.user.repository;

import com.vinn.blogspace.post.user.entity.Post;
import com.vinn.blogspace.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findByAuthor(User author, Pageable pageable);
}
