package com.api.demoimport.repository;

import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.entity.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciceRepository extends JpaRepository<Exercice,Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM esg  JOIN exercice  ON exercice.id = exercice_id " +
            "WHERE DATE_FORMAT(exercice.date_exercice, '%Y-%m-%d') = :date AND exercice.company_name = :name")
    List<Immobilisation> findEsgByND(String name, String date);
}
