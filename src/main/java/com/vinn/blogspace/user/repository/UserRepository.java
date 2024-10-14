package com.vinn.blogspace.user.repository;

import com.vinn.blogspace.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
