package com.api.demoimport.service;

import com.api.demoimport.entity.Immobilisation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImmobilisationService {

    /**
     * Interface defining service operations for immobilisation management.
     * Methods include saving an asset from a file, creating a new immobilisation,
     * finding immobilisations by name and date, and finding by ID.
     */
    void save(MultipartFile file, String date, String company_name);

    Immobilisation createImmobilisation(Immobilisation immobilisation);

    List<Immobilisation> FindImmobilisation(String name,String date);

    Optional<Immobilisation> FindByID(Long id);
}
