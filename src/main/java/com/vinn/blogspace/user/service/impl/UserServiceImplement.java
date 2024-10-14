package com.vinn.blogspace.user.service.impl;

import com.vinn.blogspace.common.services.impl.BaseServiceImpl;
import com.vinn.blogspace.user.dto.UserCreateDto;
import com.vinn.blogspace.user.dto.UserDto;
import com.vinn.blogspace.user.dto.UserUpdateDto;
import com.vinn.blogspace.user.entity.User;
import com.vinn.blogspace.user.repository.UserRepository;
import com.vinn.blogspace.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImplement extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    protected JpaRepository<User, Long> getRepository() { return userRepository; }

    @Override
    protected String getEntityName() { return "User"; }

    @Override
    public UserDto getUserById(Long id) {
        User user = findById(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return findAll(pageable).map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto createUser(UserCreateDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = create(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDto userDto) {
        User user = findById(id);
        User updatedUser = update(id, user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public Page<UserDto> searchUsers(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDto> getUsersByUsername(String username, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteUser(Long id) { delete(id); }
}
