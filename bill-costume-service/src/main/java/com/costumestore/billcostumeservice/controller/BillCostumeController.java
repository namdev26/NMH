package com.costumestore.billcostumeservice.controller;

import com.costumestore.billcostumeservice.dto.request.BillByIdsRequest;
import com.costumestore.billcostumeservice.dto.response.BillResponse;
import com.costumestore.billcostumeservice.service.BillCostumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BillCostumeController {

    private final BillCostumeService billCostumeService;

    @GetMapping("/api/bills/ids")
    public ResponseEntity<List<Long>> getBillIdsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info(">>> REQUEST GET /api/bills/ids\n    Body: (empty) | startDate={}, endDate={}", startDate, endDate);
        List<Long> result = billCostumeService.getBillIdsByDateRange(startDate, endDate);
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/bills/by-ids")
    public ResponseEntity<List<BillResponse>> getBillListByIds(
            @Valid @RequestBody BillByIdsRequest request) {
        log.info(">>> REQUEST POST /api/bills/by-ids\n    Body: {}", request);
        List<BillResponse> result = billCostumeService.getBillListByIds(request);
        log.info("<<< RESPONSE 200\n    Body: {}", result);
        return ResponseEntity.ok(result);
    }
}
