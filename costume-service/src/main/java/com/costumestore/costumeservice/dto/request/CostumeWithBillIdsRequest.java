package com.costumestore.costumeservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CostumeWithBillIdsRequest {

    @NotNull(message = "Costume ID is required")
    private Long costumeId;

    @NotEmpty(message = "Bill IDs must not be empty")
    private List<Long> billIds;
}
