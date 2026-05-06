package com.costumestore.costumeservice.service;

import com.costumestore.costumeservice.dto.request.ImportCostumeDetailRequest;
import com.costumestore.costumeservice.dto.response.CostumeDetailResponse;

import java.util.List;

public interface CostumeDetailService {
    List<CostumeDetailResponse> createImportCostumeDetail(ImportCostumeDetailRequest request);
}
