package com.costumestore.webclient.dto;

import lombok.Data;

@Data
public class RegisterResponseDto {
    private Long id;
    private String username;
    private String userType;
    private String fullName;
}
