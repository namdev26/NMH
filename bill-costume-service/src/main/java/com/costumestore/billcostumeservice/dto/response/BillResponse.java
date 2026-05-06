package com.costumestore.billcostumeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BillResponse {
    private Long id;
    private Long customerId;
    private LocalDateTime createdTime;
}
