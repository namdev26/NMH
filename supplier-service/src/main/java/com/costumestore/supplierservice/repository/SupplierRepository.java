package com.costumestore.supplierservice.repository;

import com.costumestore.supplierservice.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s ORDER BY s.id")
    List<Supplier> findSupplierList();

    @Query("SELECT s FROM Supplier s WHERE s.id = :id")
    Optional<Supplier> findSupplierById(@Param("id") Long id);
}
