package com.api.demoimport.service;

import com.api.demoimport.entity.PlanComptable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PlanComptableService {

    void save(MultipartFile file);
    List<PlanComptable> getPlanComptables();

    Optional<PlanComptable> search(Long noCompte);

}
