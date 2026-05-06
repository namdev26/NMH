package com.costumestore.webclient.service;

import com.costumestore.webclient.dto.BillStatDto;
import com.costumestore.webclient.dto.CostumeLineStatDto;
import com.costumestore.webclient.dto.CostumeStatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticClientService {

    private final RestTemplate restTemplate;

    @Value("${services.costume.url}")
    private String costumeUrl;

    @Value("${services.bill-costume.url}")
    private String billCostumeUrl;

    @Value("${services.user.url}")
    private String userUrl;

    /**
     * getCostumeLineStat: Lấy danh sách billId theo khoảng thời gian, sau đó lấy
     * BillCostumeDetail theo billIds, áp dụng Decorator pattern tạo danh sách CostumeLineStat.
     */
    public List<CostumeLineStatDto> getCostumeLineStat(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            List<Long> billIds = getBillIdsByDateRange(startTime, endTime);
            if (billIds.isEmpty()) {
                return Collections.emptyList();
            }

            Map<String, Object> body = new HashMap<>();
            body.put("billIds", billIds);
            List<SoldCostumeDetailDto> soldDetails = Objects.requireNonNullElse(
                    post(costumeUrl + "/api/bill-costume-details/by-bill-ids", body,
                            new ParameterizedTypeReference<List<SoldCostumeDetailDto>>() {}),
                    Collections.emptyList());

            Map<Long, CostumeLineStatDto> lineStats = new LinkedHashMap<>();
            for (SoldCostumeDetailDto detail : soldDetails) {
                CostumeLineStatDto stat = lineStats.computeIfAbsent(detail.getCostumeLineId(), id -> {
                    CostumeLineStatDto s = new CostumeLineStatDto();
                    s.setCostumeLineId(id);
                    s.setCostumeLineName(detail.getCostumeLineName());
                    s.setRevenue(BigDecimal.ZERO);
                    s.setTotalQuantity(0);
                    return s;
                });
                stat.setRevenue(stat.getRevenue().add(nvl(detail.getTotalAmount())));
                stat.setTotalQuantity(stat.getTotalQuantity() + nvl(detail.getQuantity()));
            }
            return lineStats.values().stream()
                    .sorted(Comparator.comparing(CostumeLineStatDto::getRevenue).reversed())
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching costume line stats: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * getCostumeStat: Lấy danh sách billId theo khoảng thời gian, sau đó lấy
     * BillCostumeDetail theo costumeLineId + billIds, áp dụng Decorator pattern tạo danh sách CostumeStat.
     */
    public List<CostumeStatDto> getCostumeStat(Long costumeLineId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            List<Long> billIds = getBillIdsByDateRange(startTime, endTime);
            if (billIds.isEmpty()) {
                return Collections.emptyList();
            }

            Map<String, Object> body = new HashMap<>();
            body.put("costumeLineId", costumeLineId);
            body.put("billIds", billIds);
            List<SoldCostumeDetailDto> soldDetails = Objects.requireNonNullElse(
                    post(costumeUrl + "/api/bill-costume-details/by-costume-line", body,
                            new ParameterizedTypeReference<List<SoldCostumeDetailDto>>() {}),
                    Collections.emptyList());

            Map<Long, CostumeStatDto> costumeStats = new LinkedHashMap<>();
            for (SoldCostumeDetailDto detail : soldDetails) {
                CostumeStatDto stat = costumeStats.computeIfAbsent(detail.getCostumeId(), id -> {
                    CostumeStatDto s = new CostumeStatDto();
                    s.setCostumeId(id);
                    s.setCostumeName(detail.getCostumeName());
                    s.setRevenue(BigDecimal.ZERO);
                    s.setTotalQuantity(0);
                    return s;
                });
                stat.setRevenue(stat.getRevenue().add(nvl(detail.getTotalAmount())));
                stat.setTotalQuantity(stat.getTotalQuantity() + nvl(detail.getQuantity()));
            }
            return costumeStats.values().stream()
                    .sorted(Comparator.comparing(CostumeStatDto::getRevenue).reversed())
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching costume stats: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * getBillListByCostume: Lấy danh sách billId theo khoảng thời gian, lấy BillCostumeDetail
     * theo costumeId + billIds, lấy thông tin Bill từ BillCostumeService, lấy tên khách hàng từ UserService.
     */
    public List<BillStatDto> getBillListByCostume(Long costumeId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            List<Long> billIds = getBillIdsByDateRange(startTime, endTime);
            if (billIds.isEmpty()) {
                return Collections.emptyList();
            }

            Map<String, Object> detailBody = new HashMap<>();
            detailBody.put("costumeId", costumeId);
            detailBody.put("billIds", billIds);
            List<SoldCostumeDetailDto> soldDetails = Objects.requireNonNullElse(
                    post(costumeUrl + "/api/bill-costume-details/by-costume", detailBody,
                            new ParameterizedTypeReference<List<SoldCostumeDetailDto>>() {}),
                    Collections.emptyList());

            if (soldDetails.isEmpty()) {
                return Collections.emptyList();
            }

            List<Long> filteredBillIds = soldDetails.stream()
                    .map(SoldCostumeDetailDto::getBillId)
                    .distinct()
                    .toList();

            Map<String, Object> billBody = new HashMap<>();
            billBody.put("ids", filteredBillIds);
            List<BillResponseDto> bills = Objects.requireNonNullElse(
                    post(billCostumeUrl + "/api/bills/by-ids", billBody,
                            new ParameterizedTypeReference<List<BillResponseDto>>() {}),
                    Collections.emptyList());

            if (bills.isEmpty()) {
                return Collections.emptyList();
            }

            List<Long> customerIds = bills.stream()
                    .map(BillResponseDto::getCustomerId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();

            Map<Long, UserResponseDto> userById = new HashMap<>();
            if (!customerIds.isEmpty()) {
                Map<String, Object> userBody = new HashMap<>();
                userBody.put("ids", customerIds);
                List<UserResponseDto> users = Objects.requireNonNullElse(
                        post(userUrl + "/api/users/by-ids", userBody,
                                new ParameterizedTypeReference<List<UserResponseDto>>() {}),
                        Collections.emptyList());
                users.forEach(user -> userById.put(user.getId(), user));
            }

            Map<Long, SoldCostumeDetailDto> detailByBillId = soldDetails.stream()
                    .collect(Collectors.toMap(SoldCostumeDetailDto::getBillId, d -> d, (a, b) -> a));

            return bills.stream()
                    .map(bill -> {
                        SoldCostumeDetailDto detail = detailByBillId.get(bill.getId());
                        UserResponseDto user = bill.getCustomerId() == null ? null : userById.get(bill.getCustomerId());

                        BillStatDto dto = new BillStatDto();
                        dto.setBillId(bill.getId());
                        dto.setCustomerId(bill.getCustomerId());
                        dto.setCustomerName(resolveCustomerName(user));
                        dto.setQuantityBought(detail == null ? 0 : nvl(detail.getQuantity()));
                        dto.setTotalAmount(detail == null ? BigDecimal.ZERO : nvl(detail.getTotalAmount()));
                        dto.setPaymentTime(bill.getCreatedTime());
                        return dto;
                    })
                    .sorted(Comparator.comparing(BillStatDto::getPaymentTime).reversed())
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching bills by costume: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Gọi BillCostumeService để lấy danh sách billId theo khoảng thời gian.
     */
    private List<Long> getBillIdsByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        String url = billCostumeUrl + "/api/bills/ids?startDate=" + startTime + "&endDate=" + endTime;
        List<Long> result = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Long>>() {}).getBody();
        return result == null ? Collections.emptyList() : result;
    }

    private String resolveCustomerName(UserResponseDto user) {
        if (user == null || user.getFullName() == null) {
            return "Khách vãng lai";
        }
        String first = user.getFullName().getFirstName();
        String last = user.getFullName().getLastName();
        if (first == null && last == null) {
            return "Khách vãng lai";
        }
        return ((first == null ? "" : first) + " " + (last == null ? "" : last)).trim();
    }

    private BigDecimal nvl(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private int nvl(Integer value) {
        return value == null ? 0 : value;
    }

    private <T> T post(String url, Object body, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType).getBody();
    }

    @lombok.Data
    private static class SoldCostumeDetailDto {
        private Long billId;
        private Long costumeId;
        private String costumeName;
        private Long costumeLineId;
        private String costumeLineName;
        private Integer quantity;
        private BigDecimal totalAmount;
    }

    @lombok.Data
    private static class BillResponseDto {
        private Long id;
        private Long customerId;
        private LocalDateTime createdTime;
    }

    @lombok.Data
    private static class UserResponseDto {
        private Long id;
        private FullNameDto fullName;
    }

    @lombok.Data
    private static class FullNameDto {
        private String firstName;
        private String lastName;
    }
}
