package com.costumestore.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String username;
    private String userType;
    private String fullName;
    private String managerCode;
    private String title;
}
