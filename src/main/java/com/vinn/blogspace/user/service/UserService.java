package com.vinn.blogspace.user.service;

import com.vinn.blogspace.common.services.BaseService;
import com.vinn.blogspace.user.dto.UserCreateDto;
import com.vinn.blogspace.user.dto.UserDto;
import com.vinn.blogspace.user.dto.UserUpdateDto;
import com.vinn.blogspace.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface UserService extends BaseService<User, Long> {
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    Page<UserDto> getAllUsers(Pageable pageable);
    UserDto createUser(UserCreateDto userDto);
    UserDto updateUser(Long id, UserUpdateDto userDto);
    Page<UserDto> searchUsers(String keyword, Pageable pageable);// TODO
    Page<UserDto> getUsersByUsername(String username, Pageable pageable); // TODO
    void deleteUser(Long id);
}
