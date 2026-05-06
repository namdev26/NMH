package com.costumestore.webclient.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ImportResultDto {
    private Long importingBillId;
    private Long managerId;
    private Long supplierId;
    private LocalDateTime importDate;
    private BigDecimal totalAmount;
    private int totalItemsImported;
    private List<CostumeDetailResult> details;
    private String message;

    @Data
    public static class CostumeDetailResult {
        private Long costumeDetailId;
        private Long costumeId;
        private String costumeName;
        private BigDecimal importPrice;
        private Integer quantity;
    }
}
