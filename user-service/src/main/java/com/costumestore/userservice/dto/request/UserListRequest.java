package com.costumestore.userservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UserListRequest {

    @NotEmpty(message = "ID list must not be empty")
    private List<Long> ids;
}
