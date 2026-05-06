package com.costumestore.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String userType;
    private AddressResponse address;
    private FullNameResponse fullName;

    // Customer fields
    private Integer rewardPoint;
    private String customerRanking;

    // Staff fields
    private BigDecimal salary;
    private String position;

    // Manager fields
    private String managerCode;
    private String title;
}
