package com.costumestore.billcostumeservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BillByIdsRequest {

    @NotEmpty(message = "Bill IDs must not be empty")
    private List<Long> ids;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
