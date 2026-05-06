package com.costumestore.billcostumeservice.repository;

import com.costumestore.billcostumeservice.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT b.id FROM Bill b WHERE b.createdTime >= :startDate AND b.createdTime <= :endDate ORDER BY b.createdTime DESC")
    List<Long> findIdsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Bill b WHERE b.id IN :ids")
    List<Bill> findByIds(@Param("ids") List<Long> ids);
}
