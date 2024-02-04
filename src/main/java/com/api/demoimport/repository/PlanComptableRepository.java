package com.api.demoimport.repository;

import com.api.demoimport.entity.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlanComptableRepository extends JpaRepository<PlanComptable,Long> {

    //Requête SQL native pour rechercher un PlanComptable correspondant au numéro de compte spécifié.
    @Query(nativeQuery = true, value = "select * from plan_comptable where no_compte = :noCompte limit 1")
    public Optional<PlanComptable> querySearchPlanComptable(Long noCompte);

}
