package com.costumestore.costumeservice.repository;

import com.costumestore.costumeservice.entity.BillCostumeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillCostumeDetailRepository extends JpaRepository<BillCostumeDetail, Long> {

    @Query("SELECT b FROM BillCostumeDetail b JOIN FETCH b.costume c JOIN FETCH c.costumeLine WHERE b.billId IN :billIds")
    List<BillCostumeDetail> findByBillIds(@Param("billIds") List<Long> billIds);

    @Query("SELECT b FROM BillCostumeDetail b JOIN FETCH b.costume c JOIN FETCH c.costumeLine cl WHERE cl.id = :costumeLineId AND b.billId IN :billIds")
    List<BillCostumeDetail> findByCostumeLineAndBillIds(@Param("costumeLineId") Long costumeLineId, @Param("billIds") List<Long> billIds);

    @Query("SELECT b FROM BillCostumeDetail b JOIN FETCH b.costume c JOIN FETCH c.costumeLine WHERE c.id = :costumeId AND b.billId IN :billIds")
    List<BillCostumeDetail> findByCostumeAndBillIds(@Param("costumeId") Long costumeId, @Param("billIds") List<Long> billIds);
}
