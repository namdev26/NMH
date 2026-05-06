package com.costumestore.costumeservice.controller;

import com.costumestore.costumeservice.dto.request.BillIdsRequest;
import com.costumestore.costumeservice.dto.request.CostumeIdsRequest;
import com.costumestore.costumeservice.dto.request.CostumeLineWithBillIdsRequest;
import com.costumestore.costumeservice.dto.request.CostumeWithBillIdsRequest;
import com.costumestore.costumeservice.dto.request.ImportCostumeDetailRequest;
import com.costumestore.costumeservice.dto.response.CostumeDetailResponse;
import com.costumestore.costumeservice.dto.response.CostumeResponse;
import com.costumestore.costumeservice.dto.response.SoldCostumeDetailResponse;
import com.costumestore.costumeservice.service.CostumeDetailService;
import com.costumestore.costumeservice.service.CostumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CostumeController {

    private final CostumeService costumeService;
    private final CostumeDetailService costumeDetailService;

    @GetMapping("/api/costumes/import-list")
    public ResponseEntity<List<CostumeResponse>> getCostumeListToImport() {
        log.info(">>> REQUEST GET /api/costumes/import-list\n    Body: (empty)");
        List<CostumeResponse> result = costumeService.getCostumeListToImport();
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/costumes/by-ids")
    public ResponseEntity<List<CostumeResponse>> getCostumeListByIds(
            @Valid @RequestBody CostumeIdsRequest request) {
        log.info(">>> REQUEST POST /api/costumes/by-ids\n    Body: {}", request);
        List<CostumeResponse> result = costumeService.getCostumeListByIds(request.getIds());
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/costume-details/import")
    public ResponseEntity<List<CostumeDetailResponse>> createImportCostumeDetail(
            @Valid @RequestBody ImportCostumeDetailRequest request) {
        log.info(">>> REQUEST POST /api/costume-details/import\n    Body: {}", request);
        List<CostumeDetailResponse> result = costumeDetailService.createImportCostumeDetail(request);
        log.info("<<< RESPONSE 201\n    Body: {}", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/api/bill-costume-details/by-bill-ids")
    public ResponseEntity<List<SoldCostumeDetailResponse>> getBillCostumeDetailListByBillIds(
            @Valid @RequestBody BillIdsRequest request) {
        log.info(">>> REQUEST POST /api/bill-costume-details/by-bill-ids\n    Body: {}", request);
        List<SoldCostumeDetailResponse> result = costumeService.getBillCostumeDetailListByBillIds(request.getBillIds());
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/bill-costume-details/by-costume-line")
    public ResponseEntity<List<SoldCostumeDetailResponse>> getBillCostumeDetailListByCostumeLineAndBillIds(
            @Valid @RequestBody CostumeLineWithBillIdsRequest request) {
        log.info(">>> REQUEST POST /api/bill-costume-details/by-costume-line\n    Body: {}", request);
        List<SoldCostumeDetailResponse> result = costumeService.getBillCostumeDetailListByCostumeLineAndBillIds(
                request.getCostumeLineId(), request.getBillIds());
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/bill-costume-details/by-costume")
    public ResponseEntity<List<SoldCostumeDetailResponse>> getBillCostumeDetailListByCostumeAndBillIds(
            @Valid @RequestBody CostumeWithBillIdsRequest request) {
        log.info(">>> REQUEST POST /api/bill-costume-details/by-costume\n    Body: {}", request);
        List<SoldCostumeDetailResponse> result = costumeService.getBillCostumeDetailListByCostumeAndBillIds(
                request.getCostumeId(), request.getBillIds());
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }
}
