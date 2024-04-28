package com.api.demoimport.repository;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.entity.Bilan.Esg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailCPCRepository extends JpaRepository<DetailCPC,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM detailcpc  JOIN exercice  ON exercice.id = exercice_id " +
            "WHERE DATE_FORMAT(exercice.date_exercice, '%Y-%m-%d') = :date AND exercice.company_name = :name")
    List<DetailCPC> findDetailCPCByND(String name, String date);
}
