package com.costumestore.webclient.model;

import com.costumestore.webclient.dto.SupplierDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable {
    private Long id;
    private String name;

    public static Supplier fromDto(SupplierDto dto) {
        if (dto == null) {
            return null;
        }
        return new Supplier(dto.getId(), dto.getName());
    }
}
