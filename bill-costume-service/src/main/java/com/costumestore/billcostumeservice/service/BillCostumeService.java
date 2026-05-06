package com.costumestore.billcostumeservice.service;

import com.costumestore.billcostumeservice.dto.request.BillByIdsRequest;
import com.costumestore.billcostumeservice.dto.response.BillResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BillCostumeService {
    List<Long> getBillIdsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<BillResponse> getBillListByIds(BillByIdsRequest request);
}
