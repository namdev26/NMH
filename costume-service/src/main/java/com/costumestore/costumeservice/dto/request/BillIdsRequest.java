package com.costumestore.costumeservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BillIdsRequest {

    @NotEmpty(message = "Bill IDs must not be empty")
    private List<Long> billIds;
}
