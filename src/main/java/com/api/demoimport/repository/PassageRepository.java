package com.api.demoimport.repository;

import com.api.demoimport.entity.Bilan.Passage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageRepository extends JpaRepository<Passage,Long> {
}
