package com.costumestore.webclient.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillStatDto {
    private Long billId;
    private Long customerId;
    private String customerName;
    private Integer quantityBought;
    private BigDecimal totalAmount;
    private LocalDateTime paymentTime;
}
