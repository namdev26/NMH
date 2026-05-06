package com.costumestore.costumeservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CostumeFilterRequest {

    @NotNull(message = "Costume ID is required")
    private Long costumeId;
}
