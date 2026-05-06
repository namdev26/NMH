package com.costumestore.costumeservice.repository;

import com.costumestore.costumeservice.entity.CostumeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumeDetailRepository extends JpaRepository<CostumeDetail, Long> {

    default List<CostumeDetail> saveAllCostumeDetail(List<CostumeDetail> costumeDetails) {
        return saveAll(costumeDetails);
    }

    List<CostumeDetail> findByCostumeId(Long costumeId);
}
