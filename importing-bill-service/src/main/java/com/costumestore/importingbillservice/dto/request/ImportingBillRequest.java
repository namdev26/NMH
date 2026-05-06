package com.costumestore.importingbillservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ImportingBillRequest {

    @NotNull(message = "Manager ID is required")
    private Long managerId;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @Valid
    @NotEmpty(message = "Costume items must not be empty")
    private List<CostumeImportItem> items;

    @Data
    public static class CostumeImportItem {

        @NotNull(message = "Costume ID is required")
        private Long costumeId;

        @NotNull(message = "Import price is required")
        @DecimalMin(value = "0.01", message = "Import price must be greater than 0")
        private BigDecimal importPrice;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

        private String note;
    }
}
