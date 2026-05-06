package com.costumestore.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 100, message = "Username phải từ 3 đến 100 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6 ký tự trở lên")
    private String password;

    private String firstName;
    private String lastName;
    private String city;
    private String street;
}
