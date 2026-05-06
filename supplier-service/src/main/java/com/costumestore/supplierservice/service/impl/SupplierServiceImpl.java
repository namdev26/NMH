package com.costumestore.supplierservice.service.impl;

import com.costumestore.supplierservice.dto.response.SupplierResponse;
import com.costumestore.supplierservice.exception.ResourceNotFoundException;
import com.costumestore.supplierservice.repository.SupplierRepository;
import com.costumestore.supplierservice.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public List<SupplierResponse> getSupplierList() {
        log.debug("Fetching all suppliers from database");
        List<SupplierResponse> result = supplierRepository.findSupplierList().stream()
                .map(s -> SupplierResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .email(s.getEmail())
                        .contact(s.getContact())
                        .build())
                .toList();
        log.info("getSupplierList - found {} supplier(s)", result.size());
        return result;
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        log.debug("Fetching supplier with id={}", id);
        return supplierRepository.findSupplierById(id)
                .map(s -> {
                    log.info("getSupplierById - found supplier id={}, name={}", s.getId(), s.getName());
                    return SupplierResponse.builder()
                            .id(s.getId())
                            .name(s.getName())
                            .email(s.getEmail())
                            .contact(s.getContact())
                            .build();
                })
                .orElseThrow(() -> {
                    log.warn("getSupplierById - supplier not found with id={}", id);
                    return new ResourceNotFoundException("Supplier not found with id: " + id);
                });
    }
}
