package com.vinn.blogspace.tag.repository;

import com.vinn.blogspace.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
