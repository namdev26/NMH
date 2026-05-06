package com.costumestore.costumeservice.mapper;

import com.costumestore.costumeservice.dto.response.*;
import com.costumestore.costumeservice.entity.*;
import org.springframework.stereotype.Component;

@Component
public class CostumeMapper {

    public CostumeLineResponse toCostumeLineResponse(CostumeLine line) {
        if (line == null) return null;
        return CostumeLineResponse.builder()
                .id(line.getId())
                .name(line.getName())
                .description(line.getDescription())
                .build();
    }

    public CostumeResponse toCostumeResponse(Costume costume) {
        if (costume == null) return null;
        return CostumeResponse.builder()
                .id(costume.getId())
                .name(costume.getName())
                .category(costume.getCategory())
                .size(costume.getSize())
                .color(costume.getColor())
                .material(costume.getMaterial())
                .sellPrice(costume.getSellPrice())
                .costumeLine(toCostumeLineResponse(costume.getCostumeLine()))
                .supplierId(costume.getSupplierId())
                .build();
    }

    public CostumeDetailResponse toCostumeDetailResponse(CostumeDetail detail) {
        if (detail == null) return null;
        return CostumeDetailResponse.builder()
                .id(detail.getId())
                .costumeId(detail.getCostume().getId())
                .costumeName(detail.getCostume().getName())
                .importPrice(detail.getImportPrice())
                .quantity(detail.getQuantity())
                .note(detail.getNote())
                .importingBillId(detail.getImportingBillId())
                .build();
    }

    public SoldCostumeDetailResponse toSoldCostumeDetailResponse(BillCostumeDetail bcd) {
        if (bcd == null) return null;
        return SoldCostumeDetailResponse.builder()
                .id(bcd.getId())
                .billId(bcd.getBillId())
                .costumeId(bcd.getCostume().getId())
                .costumeName(bcd.getCostume().getName())
                .costumeLineId(bcd.getCostume().getCostumeLine().getId())
                .costumeLineName(bcd.getCostume().getCostumeLine().getName())
                .quantity(bcd.getQuantity())
                .price(bcd.getPrice())
                .totalAmount(bcd.getPrice().multiply(java.math.BigDecimal.valueOf(bcd.getQuantity())))
                .build();
    }
}
