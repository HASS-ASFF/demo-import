package com.api.demoimport.repository;

import com.api.demoimport.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating Passage entities.
 */
public interface PassageRepository extends JpaRepository<Passage,Long> {
    @Query(nativeQuery = true, value = "SELECT *  FROM Passage WHERE LEFT(date,10) = :date ")
    List<Passage> findPassagesByDate(String date);

    @Query(nativeQuery = true, value = "SELECT *  FROM Passage WHERE name = :name AND LEFT(date,10) = :date")
    Optional<Passage> findPassageByND(String name, String date);

    boolean existsById(Long id);
}
