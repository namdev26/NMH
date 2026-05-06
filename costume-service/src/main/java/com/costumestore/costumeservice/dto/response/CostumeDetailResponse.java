package com.costumestore.costumeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CostumeDetailResponse {
    private Long id;
    private Long costumeId;
    private String costumeName;
    private BigDecimal importPrice;
    private Integer quantity;
    private String note;
    private Long importingBillId;
}
