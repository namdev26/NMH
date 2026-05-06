package com.costumestore.costumeservice.service.impl;

import com.costumestore.costumeservice.dto.request.ImportCostumeDetailRequest;
import com.costumestore.costumeservice.dto.response.CostumeDetailResponse;
import com.costumestore.costumeservice.entity.Costume;
import com.costumestore.costumeservice.entity.CostumeDetail;
import com.costumestore.costumeservice.exception.ResourceNotFoundException;
import com.costumestore.costumeservice.mapper.CostumeMapper;
import com.costumestore.costumeservice.repository.CostumeDetailRepository;
import com.costumestore.costumeservice.repository.CostumeRepository;
import com.costumestore.costumeservice.service.CostumeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CostumeDetailServiceImpl implements CostumeDetailService {

    private final CostumeDetailRepository costumeDetailRepository;
    private final CostumeRepository costumeRepository;
    private final CostumeMapper costumeMapper;

    @Override
    @Transactional
    public List<CostumeDetailResponse> createImportCostumeDetail(ImportCostumeDetailRequest request) {
        log.info("Importing {} costume details for bill id: {}",
                request.getItems().size(), request.getImportingBillId());

        List<CostumeDetail> details = new ArrayList<>();

        for (ImportCostumeDetailRequest.CostumeImportItem item : request.getItems()) {
            Costume costume = costumeRepository.findById(item.getCostumeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Costume not found with id: " + item.getCostumeId()));

            costume.setStockQuantity(costume.getStockQuantity() + item.getQuantity());
            costumeRepository.save(costume);

            details.add(CostumeDetail.builder()
                    .costume(costume)
                    .importPrice(item.getImportPrice())
                    .quantity(item.getQuantity())
                    .note(item.getNote())
                    .importingBillId(request.getImportingBillId())
                    .build());
        }

        List<CostumeDetail> savedDetails = costumeDetailRepository.saveAllCostumeDetail(details);
        log.info("Successfully imported {} costume details", savedDetails.size());
        return savedDetails.stream()
                .map(costumeMapper::toCostumeDetailResponse)
                .toList();
    }
}
