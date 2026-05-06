package com.costumestore.webclient.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostumeStatDto {
    private Long costumeId;
    private String costumeName;
    private String category;
    private BigDecimal sellPrice;
    private BigDecimal revenue;
    private Integer totalQuantity;
}
