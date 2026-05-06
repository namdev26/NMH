package com.costumestore.costumeservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CostumeLineResponse {
    private Long id;
    private String name;
    private String description;
}
