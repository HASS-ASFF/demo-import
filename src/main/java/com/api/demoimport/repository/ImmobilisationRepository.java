package com.api.demoimport.repository;


import com.api.demoimport.entity.Bilan.Immobilisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImmobilisationRepository extends JpaRepository<Immobilisation,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM immobilisation  JOIN exercice  ON exercice.id = exercice_id " +
            "WHERE DATE_FORMAT(exercice.date_exercice, '%Y-%m-%d') = :date AND exercice.company_name = :name")
    List<Immobilisation> findImmobilisationByND(String name, String date);

}
