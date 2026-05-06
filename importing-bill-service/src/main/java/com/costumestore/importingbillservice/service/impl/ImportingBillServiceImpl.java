package com.costumestore.importingbillservice.service.impl;

import com.costumestore.importingbillservice.dto.request.ImportingBillRequest;
import com.costumestore.importingbillservice.dto.response.ImportingBillResponse;
import com.costumestore.importingbillservice.entity.ImportingBill;
import com.costumestore.importingbillservice.repository.ImportingBillRepository;
import com.costumestore.importingbillservice.service.ImportingBillService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportingBillServiceImpl implements ImportingBillService {

    private final ImportingBillRepository importingBillRepository;
    private final RestTemplate restTemplate;

    @Value("${services.supplier.url}")
    private String supplierUrl;

    @Value("${services.costume.url}")
    private String costumeUrl;

    @Override
    @Transactional
    public ImportingBillResponse createImportingBill(ImportingBillRequest request) {
        log.info("Creating importing bill for manager: {}, supplier: {}",
                request.getManagerId(), request.getSupplierId());

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Costume items must not be empty");
        }

        verifySupplier(request.getSupplierId());

        List<Long> costumeIds = request.getItems().stream()
                .map(ImportingBillRequest.CostumeImportItem::getCostumeId)
                .distinct()
                .toList();
        verifyCostumes(costumeIds);

        BigDecimal totalAmount = request.getItems().stream()
                .map(item -> item.getImportPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ImportingBill bill = ImportingBill.builder()
                .managerId(request.getManagerId())
                .supplierId(request.getSupplierId())
                .importDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .build();

        ImportingBill saved = importingBillRepository.saveImportingBill(bill);
        log.info("Importing bill created with id: {}", saved.getId());

        try {
            List<CostumeDetailResult> details = createCostumeDetails(saved.getId(), request.getItems());
            int totalItemsImported = details.stream()
                    .mapToInt(d -> d.getQuantity() == null ? 0 : d.getQuantity())
                    .sum();

            return ImportingBillResponse.builder()
                    .id(saved.getId())
                    .managerId(saved.getManagerId())
                    .supplierId(saved.getSupplierId())
                    .importDate(saved.getImportDate())
                    .totalAmount(saved.getTotalAmount())
                    .totalItemsImported(totalItemsImported)
                    .details(details.stream()
                            .map(d -> ImportingBillResponse.CostumeDetailResult.builder()
                                    .costumeDetailId(d.getId())
                                    .costumeId(d.getCostumeId())
                                    .costumeName(d.getCostumeName())
                                    .importPrice(d.getImportPrice())
                                    .quantity(d.getQuantity())
                                    .build())
                            .toList())
                    .build();
        } catch (Exception e) {
            log.error("Failed to create costume details for bill {}. Performing compensating transaction.", saved.getId());
            importingBillRepository.deleteImportingBill(saved.getId());
            throw new RuntimeException("Failed to create costume details: " + e.getMessage(), e);
        }
    }

    private void verifySupplier(Long supplierId) {
        try {
            restTemplate.getForObject(supplierUrl + "/api/suppliers/" + supplierId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Supplier not found with id: " + supplierId);
        }
    }

    private void verifyCostumes(List<Long> costumeIds) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("ids", costumeIds);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            restTemplate.exchange(costumeUrl + "/api/costumes/by-ids", HttpMethod.POST, entity,
                    new ParameterizedTypeReference<List<Object>>() {});
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("One or more costumes not found: " + e.getMessage());
        }
    }

    private List<CostumeDetailResult> createCostumeDetails(Long importingBillId,
            List<ImportingBillRequest.CostumeImportItem> items) {
        Map<String, Object> body = new HashMap<>();
        body.put("importingBillId", importingBillId);
        body.put("items", items.stream().map(item -> {
            Map<String, Object> i = new HashMap<>();
            i.put("costumeId", item.getCostumeId());
            i.put("importPrice", item.getImportPrice());
            i.put("quantity", item.getQuantity());
            i.put("note", item.getNote());
            return i;
        }).toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        List<CostumeDetailResult> result = restTemplate.exchange(
                costumeUrl + "/api/costume-details/import",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<CostumeDetailResult>>() {}).getBody();

        return result == null ? List.of() : result;
    }

    @Data
    private static class CostumeDetailResult {
        private Long id;
        private Long costumeId;
        private String costumeName;
        private BigDecimal importPrice;
        private Integer quantity;
    }
}
