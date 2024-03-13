package com.api.demoimport.repository;

import com.api.demoimport.entity.Bilan.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PassageRepository extends JpaRepository<Passage,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Passage WHERE date LIKE ':bilanDate%' ")
    public List<Passage> findPassagesByBilanDate(@Param("bilanDate") String bilanDate);

}
