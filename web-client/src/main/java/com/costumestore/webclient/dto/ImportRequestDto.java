package com.costumestore.webclient.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ImportRequestDto {
    private Long managerId;
    private Long supplierId;
    private List<CostumeImportItem> items;

    @Data
    public static class CostumeImportItem {
        private Long costumeId;
        private BigDecimal importPrice;
        private Integer quantity;
        private String note;
    }
}
