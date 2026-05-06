package com.costumestore.webclient.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostumeLineStatDto {
    private Long costumeLineId;
    private String costumeLineName;
    private String description;
    private BigDecimal revenue;
    private Integer totalQuantity;
}
