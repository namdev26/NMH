package com.costumestore.webclient.service;

import com.costumestore.webclient.dto.ImportRequestDto;
import com.costumestore.webclient.dto.ImportResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportHandleClientService {

    private final RestTemplate restTemplate;

    @Value("${services.importing-bill.url}")
    private String importingBillUrl;

    /**
     * handleImportBill: Gửi yêu cầu tới ImportingBillService để tạo phiếu nhập.
     * ImportingBillService sẽ tự xác minh nhà cung cấp, trang phục, lưu phiếu nhập
     * và tạo chi tiết nhập (kèm compensating transaction nếu thất bại).
     */
    public ImportResultDto handleImportBill(ImportRequestDto request) {
        Map<String, Object> body = new HashMap<>();
        body.put("managerId", request.getManagerId());
        body.put("supplierId", request.getSupplierId());
        body.put("items", request.getItems().stream().map(item -> {
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

        log.debug("Calling ImportingBillService to create importing bill");
        ImportingBillResponse response = restTemplate.postForObject(
                importingBillUrl + "/api/importing-bills", entity, ImportingBillResponse.class);

        if (response == null) {
            throw new RuntimeException("ImportingBillService returned null response");
        }

        BigDecimal totalAmount = response.getTotalAmount() != null
                ? response.getTotalAmount()
                : request.getItems().stream()
                .map(item -> item.getImportPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ImportResultDto result = new ImportResultDto();
        result.setImportingBillId(response.getId());
        result.setManagerId(response.getManagerId());
        result.setSupplierId(response.getSupplierId());
        result.setImportDate(response.getImportDate());
        result.setTotalAmount(totalAmount);
        result.setTotalItemsImported(response.getTotalItemsImported());
        result.setMessage("Nhập trang phục thành công! Đã tạo phiếu nhập #" + response.getId());

        if (response.getDetails() != null) {
            List<ImportResultDto.CostumeDetailResult> detailResults = response.getDetails().stream()
                    .map(d -> {
                        ImportResultDto.CostumeDetailResult item = new ImportResultDto.CostumeDetailResult();
                        item.setCostumeDetailId(d.getCostumeDetailId());
                        item.setCostumeId(d.getCostumeId());
                        item.setCostumeName(d.getCostumeName());
                        item.setImportPrice(d.getImportPrice());
                        item.setQuantity(d.getQuantity());
                        return item;
                    }).toList();
            result.setDetails(detailResults);
        }

        return result;
    }

    @lombok.Data
    private static class ImportingBillResponse {
        private Long id;
        private Long managerId;
        private Long supplierId;
        private java.time.LocalDateTime importDate;
        private BigDecimal totalAmount;
        private int totalItemsImported;
        private List<CostumeDetailResultDto> details;
    }

    @lombok.Data
    private static class CostumeDetailResultDto {
        private Long costumeDetailId;
        private Long costumeId;
        private String costumeName;
        private BigDecimal importPrice;
        private Integer quantity;
    }
}
