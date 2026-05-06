package com.costumestore.webclient.model;

import com.costumestore.webclient.dto.ImportRequestDto;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Assembles an importing bill: builds {@link Bill}, attaches details, supplier, manager, total, then maps to {@link ImportRequestDto}.
 */
@Getter
public class ImportingBill implements Serializable {

    private final Bill bill;

    private ImportingBill(Bill bill) {
        this.bill = bill;
    }

    /** Wraps a newly constructed {@link Bill} for the import flow. */
    public static ImportingBill forBill(Bill bill) {
        return new ImportingBill(bill);
    }

    public void setCostumeDetails(List<CostumeDetail> details) {
        bill.setCostumeDetails(details != null ? new ArrayList<>(details) : new ArrayList<>());
    }

    public void setSupplier(Supplier supplier) {
        bill.setSupplier(supplier);
    }

    public void setManager(Manager manager) {
        bill.setManager(manager);
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        bill.setTotalAmount(totalAmount);
    }

    public ImportRequestDto toImportRequest() {
        ImportRequestDto dto = new ImportRequestDto();
        dto.setManagerId(bill.getManager().getId());
        dto.setSupplierId(bill.getSupplier().getId());
        List<ImportRequestDto.CostumeImportItem> items = new ArrayList<>();
        for (CostumeDetail cd : bill.getCostumeDetails()) {
            ImportRequestDto.CostumeImportItem item = new ImportRequestDto.CostumeImportItem();
            item.setCostumeId(cd.getCostume().getId());
            item.setImportPrice(cd.getImportPrice());
            item.setQuantity(cd.getQuantity());
            item.setNote(cd.getNote() != null ? cd.getNote() : "");
            items.add(item);
        }
        dto.setItems(items);
        return dto;
    }
}
