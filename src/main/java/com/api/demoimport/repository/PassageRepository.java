package com.api.demoimport.repository;

import com.api.demoimport.entity.Bilan.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PassageRepository extends JpaRepository<Passage,Long> {
    @Query(nativeQuery = true, value = "SELECT name, amountPlus, amountMinus, DATE(date)   FROM Passage WHERE date LIKE ':bilanDate%' ")
    List<Passage> findPassagesByBilanDate(@Param("bilanDate") String bilanDate);

}
