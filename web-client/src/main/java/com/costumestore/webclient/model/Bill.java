package com.costumestore.webclient.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Bill implements Serializable {
    private Long id;
    private BigDecimal totalAmount;
    private Manager manager;
    private Supplier supplier;
    private List<CostumeDetail> costumeDetails = new ArrayList<>();
}
