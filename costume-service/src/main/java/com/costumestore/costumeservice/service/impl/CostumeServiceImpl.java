package com.costumestore.costumeservice.service.impl;

import com.costumestore.costumeservice.dto.response.CostumeResponse;
import com.costumestore.costumeservice.dto.response.SoldCostumeDetailResponse;
import com.costumestore.costumeservice.exception.ResourceNotFoundException;
import com.costumestore.costumeservice.mapper.CostumeMapper;
import com.costumestore.costumeservice.repository.BillCostumeDetailRepository;
import com.costumestore.costumeservice.repository.CostumeRepository;
import com.costumestore.costumeservice.service.CostumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CostumeServiceImpl implements CostumeService {

    private final CostumeRepository costumeRepository;
    private final BillCostumeDetailRepository billCostumeDetailRepository;
    private final CostumeMapper costumeMapper;

    @Override
    public List<CostumeResponse> getCostumeListToImport() {
        log.debug("Fetching costume import list");
        return costumeRepository.findCostumeListToImport().stream()
                .map(costumeMapper::toCostumeResponse)
                .toList();
    }

    @Override
    public List<CostumeResponse> getCostumeListByIds(List<Long> ids) {
        log.debug("Fetching costumes by ids: {}", ids);
        List<CostumeResponse> result = costumeRepository.findCostumeListByIds(ids).stream()
                .map(costumeMapper::toCostumeResponse)
                .toList();
        if (result.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more costumes not found for the provided IDs");
        }
        return result;
    }

    @Override
    public List<SoldCostumeDetailResponse> getBillCostumeDetailListByBillIds(List<Long> billIds) {
        log.debug("Fetching BillCostumeDetail list by billIds: {}", billIds);
        return billCostumeDetailRepository.findByBillIds(billIds).stream()
                .map(costumeMapper::toSoldCostumeDetailResponse)
                .toList();
    }

    @Override
    public List<SoldCostumeDetailResponse> getBillCostumeDetailListByCostumeLineAndBillIds(Long costumeLineId, List<Long> billIds) {
        log.debug("Fetching BillCostumeDetail list by costumeLineId: {} and billIds: {}", costumeLineId, billIds);
        return billCostumeDetailRepository.findByCostumeLineAndBillIds(costumeLineId, billIds).stream()
                .map(costumeMapper::toSoldCostumeDetailResponse)
                .toList();
    }

    @Override
    public List<SoldCostumeDetailResponse> getBillCostumeDetailListByCostumeAndBillIds(Long costumeId, List<Long> billIds) {
        log.debug("Fetching BillCostumeDetail list by costumeId: {} and billIds: {}", costumeId, billIds);
        return billCostumeDetailRepository.findByCostumeAndBillIds(costumeId, billIds).stream()
                .map(costumeMapper::toSoldCostumeDetailResponse)
                .toList();
    }
}
