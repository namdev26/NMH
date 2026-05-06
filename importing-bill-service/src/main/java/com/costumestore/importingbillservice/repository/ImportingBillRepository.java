package com.costumestore.importingbillservice.repository;

import com.costumestore.importingbillservice.entity.ImportingBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportingBillRepository extends JpaRepository<ImportingBill, Long> {

    default ImportingBill saveImportingBill(ImportingBill importingBill) {
        return save(importingBill);
    }

    default void deleteImportingBill(Long id) {
        deleteById(id);
    }
}
