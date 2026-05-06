package com.costumestore.webclient.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private Long id;
    private String username;
    private String userType;
    private String fullName;
    private String managerCode;
    private String title;
}
