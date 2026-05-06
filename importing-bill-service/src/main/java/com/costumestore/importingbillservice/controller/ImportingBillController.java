package com.costumestore.importingbillservice.controller;

import com.costumestore.importingbillservice.dto.request.ImportingBillRequest;
import com.costumestore.importingbillservice.dto.response.ImportingBillResponse;
import com.costumestore.importingbillservice.service.ImportingBillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImportingBillController {

    private final ImportingBillService importingBillService;

    @PostMapping("/api/importing-bills")
    public ResponseEntity<ImportingBillResponse> createImportingBill(
            @Valid @RequestBody ImportingBillRequest request) {
        log.info(">>> REQUEST POST /api/importing-bills\n    Body: {}", request);
        ImportingBillResponse result = importingBillService.createImportingBill(request);
        log.info("<<< RESPONSE 201\n    Body: {}", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
