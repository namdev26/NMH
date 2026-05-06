package com.costumestore.billcostumeservice.service.impl;

import com.costumestore.billcostumeservice.dto.request.BillByIdsRequest;
import com.costumestore.billcostumeservice.dto.response.BillResponse;
import com.costumestore.billcostumeservice.repository.BillRepository;
import com.costumestore.billcostumeservice.service.BillCostumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BillCostumeServiceImpl implements BillCostumeService {

    private final BillRepository billRepository;

    @Override
    public List<Long> getBillIdsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Fetching bill IDs between {} and {}", startDate, endDate);
        return billRepository.findIdsByDateRange(startDate, endDate);
    }

    @Override
    public List<BillResponse> getBillListByIds(BillByIdsRequest request) {
        log.debug("Fetching bills by ids: {}", request.getIds());
        return billRepository.findByIds(request.getIds()).stream()
                .map(b -> BillResponse.builder()
                        .id(b.getId())
                        .customerId(b.getCustomerId())
                        .createdTime(b.getCreatedTime())
                        .build())
                .toList();
    }
}
