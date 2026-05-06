package com.costumestore.costumeservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CostumeLineFilterRequest {

    @NotNull(message = "Costume line ID is required")
    private Long costumeLineId;
}
