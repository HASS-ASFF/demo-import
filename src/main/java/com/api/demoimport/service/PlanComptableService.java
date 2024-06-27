package com.api.demoimport.service;

import com.api.demoimport.entity.PlanComptable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PlanComptableService {

    /**
     * Interface defining service operations for managing the Plan Comptable.
     * Methods include saving a Plan Comptable from a file, retrieving all Plan Comptable entries,
     * and searching for a specific Plan Comptable entry by its account number.
     */

    void save(MultipartFile file);
    List<PlanComptable> getPlanComptables();

    Optional<PlanComptable> search(Long noCompte);

}
