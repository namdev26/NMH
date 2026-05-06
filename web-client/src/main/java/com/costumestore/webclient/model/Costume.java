package com.costumestore.webclient.model;

import com.costumestore.webclient.dto.CostumeDto;
import com.costumestore.webclient.dto.CostumeLineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Costume implements Serializable {
    private Long id;
    private String name;
    private String size;
    private String color;
    private BigDecimal sellPrice;
    private CostumeLine costumeLine;
    private Long supplierId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CostumeLine implements Serializable {
        private Long id;
        private String name;
    }

    public static Costume fromDto(CostumeDto dto) {
        if (dto == null) {
            return null;
        }
        CostumeLine line = null;
        CostumeLineDto ld = dto.getCostumeLine();
        if (ld != null) {
            line = new CostumeLine(ld.getId(), ld.getName());
        }
        Costume costume = new Costume(
                dto.getId(),
                dto.getName(),
                dto.getSize(),
                dto.getColor(),
                dto.getSellPrice(),
                line,
                dto.getSupplierId());
        return costume;
    }
}
