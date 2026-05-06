package com.costumestore.supplierservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierResponse {
    private Long id;
    private String name;
    private String email;
    private String contact;
}
