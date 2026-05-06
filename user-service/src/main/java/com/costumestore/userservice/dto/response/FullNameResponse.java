package com.costumestore.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullNameResponse {
    private Long id;
    private String firstName;
    private String lastName;

    public String getDisplayName() {
        return firstName + " " + lastName;
    }
}
