package com.costumestore.costumeservice.repository;

import com.costumestore.costumeservice.entity.Costume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumeRepository extends JpaRepository<Costume, Long> {

    @Query("SELECT c FROM Costume c JOIN FETCH c.costumeLine ORDER BY c.name")
    List<Costume> findCostumeListToImport();

    @Query("SELECT c FROM Costume c JOIN FETCH c.costumeLine WHERE c.id IN :ids")
    List<Costume> findCostumeListByIds(@Param("ids") List<Long> ids);
}
