package com.costumestore.userservice.service.impl;

import com.costumestore.userservice.dto.response.UserResponse;
import com.costumestore.userservice.exception.ResourceNotFoundException;
import com.costumestore.userservice.mapper.UserMapper;
import com.costumestore.userservice.repository.UserRepository;
import com.costumestore.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUserListByIds(List<Long> ids) {
        log.debug("Fetching users by ids: {}", ids);
        return userRepository.findByIds(ids).stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.debug("Fetching user by id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
