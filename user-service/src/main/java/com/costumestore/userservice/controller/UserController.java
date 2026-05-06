package com.costumestore.userservice.controller;

import com.costumestore.userservice.dto.request.LoginRequest;
import com.costumestore.userservice.dto.request.UserListRequest;
import com.costumestore.userservice.dto.response.LoginResponse;
import com.costumestore.userservice.dto.response.UserResponse;
import com.costumestore.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users/by-ids")
    public ResponseEntity<List<UserResponse>> getUserListByIds(@Valid @RequestBody UserListRequest request) {
        log.info(">>> REQUEST POST /api/users/by-ids\n    Body: {}", request);
        List<UserResponse> result = userService.getUserListByIds(request.getIds());
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }
}
