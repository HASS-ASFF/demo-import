package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Immobilisation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImmobilisationService {

    void save(MultipartFile file, String date, String company_name);

    Immobilisation createImmobilisation(Immobilisation immobilisation);

    List<Immobilisation> FindImmobilisation(String name,String date);

    Optional<Immobilisation> FindByID(Long id);
}
