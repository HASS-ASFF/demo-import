package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Immobilisation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImmobilisationService {

    void save(MultipartFile file, String date, String company_name);

    Immobilisation createImmobilisation(Immobilisation immobilisation);
    Immobilisation updateImmobilisation(Immobilisation updatedImmobilisation,String date);
    List<Immobilisation> FilterImmobilisation(List<Immobilisation> immobilisations, String m_name);

    List<Immobilisation> FindImmobilisation(String name,String date);

    Optional<Immobilisation> FindByID(Long id);
}
