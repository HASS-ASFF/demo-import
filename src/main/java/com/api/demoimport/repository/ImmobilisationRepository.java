package com.api.demoimport.repository;


import com.api.demoimport.entity.Immobilisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmobilisationRepository extends JpaRepository<Immobilisation,Long> {
}
