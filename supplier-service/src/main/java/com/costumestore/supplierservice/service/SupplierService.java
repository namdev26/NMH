package com.costumestore.supplierservice.service;

import com.costumestore.supplierservice.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> getSupplierList();
    SupplierResponse getSupplierById(Long id);
}
