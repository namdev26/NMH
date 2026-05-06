package com.costumestore.supplierservice.controller;

import com.costumestore.supplierservice.dto.response.SupplierResponse;
import com.costumestore.supplierservice.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/api/suppliers")
    public ResponseEntity<List<SupplierResponse>> getSupplierList() {
        log.info(">>> REQUEST GET /api/suppliers\n    Body: (empty)");
        List<SupplierResponse> result = supplierService.getSupplierList();
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/suppliers/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long id) {
        log.info(">>> REQUEST GET /api/suppliers/{}\n    Body: (empty)", id);
        SupplierResponse result = supplierService.getSupplierById(id);
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }
}
