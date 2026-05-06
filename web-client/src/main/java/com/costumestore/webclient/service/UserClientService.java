package com.costumestore.webclient.service;

import com.costumestore.webclient.dto.LoginRequestDto;
import com.costumestore.webclient.dto.LoginResponseDto;
import com.costumestore.webclient.dto.RegisterRequestDto;
import com.costumestore.webclient.dto.RegisterResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserClientService {

    private final RestTemplate restTemplate;

    @Value("${services.user.url}")
    private String userServiceUrl;

    /**
     * Gọi user-service để đăng nhập.
     * Trả về LoginResponseDto nếu thành công, ném HttpClientErrorException nếu thất bại.
     */
    public LoginResponseDto login(LoginRequestDto request) {
        String url = userServiceUrl + "/api/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequestDto> entity = new HttpEntity<>(request, headers);
        log.info("Calling user-service login: username={}", request.getUsername());
        return restTemplate.postForObject(url, entity, LoginResponseDto.class);
    }

    /**
     * Gọi user-service để đăng ký tài khoản CUSTOMER mới.
     * Trả về RegisterResponseDto nếu thành công, ném HttpClientErrorException nếu thất bại.
     */
    public RegisterResponseDto register(RegisterRequestDto request) {
        String url = userServiceUrl + "/api/auth/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequestDto> entity = new HttpEntity<>(request, headers);
        log.info("Calling user-service register: username={}", request.getUsername());
        return restTemplate.postForObject(url, entity, RegisterResponseDto.class);
    }
}
