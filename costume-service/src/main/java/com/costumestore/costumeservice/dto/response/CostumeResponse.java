package com.costumestore.costumeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CostumeResponse {
    private Long id;
    private String name;
    private String category;
    private String size;
    private String color;
    private String material;
    private BigDecimal sellPrice;
    private CostumeLineResponse costumeLine;
    private Long supplierId;
}
