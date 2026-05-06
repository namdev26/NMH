package com.costumestore.costumeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SoldCostumeDetailResponse {
    private Long id;
    private Long billId;
    private Long costumeId;
    private String costumeName;
    private Long costumeLineId;
    private String costumeLineName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
}
