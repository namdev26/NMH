package com.costumestore.webclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostumeDetail implements Serializable {
    private Costume costume;
    private BigDecimal importPrice;
    private Integer quantity;
    private String note;
}
