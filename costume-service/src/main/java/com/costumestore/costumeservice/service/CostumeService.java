package com.costumestore.costumeservice.service;

import com.costumestore.costumeservice.dto.response.CostumeResponse;
import com.costumestore.costumeservice.dto.response.SoldCostumeDetailResponse;

import java.util.List;

public interface CostumeService {
    List<CostumeResponse> getCostumeListToImport();
    List<CostumeResponse> getCostumeListByIds(List<Long> ids);
    List<SoldCostumeDetailResponse> getBillCostumeDetailListByBillIds(List<Long> billIds);
    List<SoldCostumeDetailResponse> getBillCostumeDetailListByCostumeLineAndBillIds(Long costumeLineId, List<Long> billIds);
    List<SoldCostumeDetailResponse> getBillCostumeDetailListByCostumeAndBillIds(Long costumeId, List<Long> billIds);
}
