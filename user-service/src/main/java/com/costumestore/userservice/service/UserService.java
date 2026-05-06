package com.costumestore.userservice.service;

import com.costumestore.userservice.dto.request.LoginRequest;
import com.costumestore.userservice.dto.response.LoginResponse;
import com.costumestore.userservice.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    List<UserResponse> getUserListByIds(List<Long> ids);
    UserResponse getUserById(Long id);
    LoginResponse login(LoginRequest request);
}
