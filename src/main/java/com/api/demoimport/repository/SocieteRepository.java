package com.api.demoimport.repository;

import com.api.demoimport.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface SocieteRepository extends JpaRepository<Societe,Long> {

    @Query(nativeQuery = true, value = "select * from societe where social_reason = :name LIMIT 1")
    Optional<Societe> findBySocialReason(String name);

    @Query(nativeQuery = true,value = "select DATE_FORMAT(start_activity_date,'%Y-%m-%d') from societe where social_reason = :name")
    String getStartDateActivity(String name);
}
