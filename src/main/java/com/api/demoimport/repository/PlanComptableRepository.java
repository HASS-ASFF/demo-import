package com.api.demoimport.repository;

import com.api.demoimport.entity.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for accessing and manipulating PlanComptable entities.
 */
public interface PlanComptableRepository extends JpaRepository<PlanComptable,Long> {

    // Fetching data from PlanComptable BY ACOOUNT NUMBER
    @Query(nativeQuery = true, value = "select * from plan_comptable where LEFT(no_compte,4) = LEFT(:noCompte,4)  limit 1")
    Optional<PlanComptable> querySearchPlanComptable(Long noCompte);

}
