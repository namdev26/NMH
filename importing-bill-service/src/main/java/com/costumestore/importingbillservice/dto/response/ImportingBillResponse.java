package com.costumestore.importingbillservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ImportingBillResponse {
    private Long id;
    private Long managerId;
    private Long supplierId;
    private LocalDateTime importDate;
    private BigDecimal totalAmount;
    private int totalItemsImported;
    private List<CostumeDetailResult> details;

    @Data
    @Builder
    public static class CostumeDetailResult {
        private Long costumeDetailId;
        private Long costumeId;
        private String costumeName;
        private BigDecimal importPrice;
        private Integer quantity;
    }
}
