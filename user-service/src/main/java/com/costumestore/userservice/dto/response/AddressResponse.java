package com.costumestore.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Long id;
    private String city;
    private String street;
}
