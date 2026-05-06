package com.costumestore.webclient.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String street;
}
