package com.costumestore.webclient.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostumeDto {
    private Long id;
    private String name;
    private String category;
    private String size;
    private String color;
    private String material;
    private BigDecimal sellPrice;
    private CostumeLineDto costumeLine;
    private Long supplierId;
}
