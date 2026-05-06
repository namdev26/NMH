package com.costumestore.costumeservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CostumeIdsRequest {

    @NotEmpty(message = "Costume IDs must not be empty")
    private List<Long> ids;
}
