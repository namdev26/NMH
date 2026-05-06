package com.costumestore.userservice.service.impl;

import com.costumestore.userservice.dto.request.LoginRequest;
import com.costumestore.userservice.dto.request.RegisterRequest;
import com.costumestore.userservice.dto.response.LoginResponse;
import com.costumestore.userservice.dto.response.RegisterResponse;
import com.costumestore.userservice.dto.response.UserResponse;
import com.costumestore.userservice.entity.Address;
import com.costumestore.userservice.entity.Customer;
import com.costumestore.userservice.entity.FullName;
import com.costumestore.userservice.entity.Manager;
import com.costumestore.userservice.entity.User;
import com.costumestore.userservice.exception.ResourceNotFoundException;
import com.costumestore.userservice.mapper.UserMapper;
import com.costumestore.userservice.repository.UserRepository;
import com.costumestore.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public LoginResponse login(LoginRequest request) {
        log.debug("Authenticating username: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sai tên đăng nhập hoặc mật khẩu"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sai tên đăng nhập hoặc mật khẩu");
        }

        if (!(user instanceof Manager manager)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tài khoản này không có quyền truy cập trang quản lý");
        }

        String fullName = null;
        if (manager.getFullName() != null) {
            String firstName = manager.getFullName().getFirstName();
            String lastName = manager.getFullName().getLastName();
            fullName = ((firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName)).trim();
            if (fullName.isBlank()) {
                fullName = null;
            }
        }

        return LoginResponse.builder()
                .id(manager.getId())
                .username(manager.getUsername())
                .userType("MANAGER")
                .fullName(fullName)
                .managerCode(manager.getManagerCode())
                .title(manager.getTitle())
                .build();
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        log.debug("Registering new customer: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập đã tồn tại");
        }

        Address address = null;
        if (request.getCity() != null || request.getStreet() != null) {
            address = Address.builder()
                    .city(request.getCity())
                    .street(request.getStreet())
                    .build();
        }

        FullName fullNameEntity = null;
        if (request.getFirstName() != null || request.getLastName() != null) {
            fullNameEntity = FullName.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .build();
        }

        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setPassword(request.getPassword());
        customer.setAddress(address);
        customer.setFullName(fullNameEntity);
        customer.setRewardPoint(0);
        customer.setCustomerRanking("BRONZE");

        Customer saved = userRepository.save(customer);

        String fullName = null;
        if (saved.getFullName() != null) {
            String first = saved.getFullName().getFirstName();
            String last = saved.getFullName().getLastName();
            fullName = ((first == null ? "" : first) + " " + (last == null ? "" : last)).trim();
            if (fullName.isBlank()) fullName = null;
        }

        log.info("Registered new customer id={} username={}", saved.getId(), saved.getUsername());
        return RegisterResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .userType("CUSTOMER")
                .fullName(fullName)
                .build();
    }
}
