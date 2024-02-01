package com.api.demoimport.repository;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BalanceDetailRepository extends JpaRepository<BalanceDetail,Long> {

    /*
    @Query(nativeQuery = true, value = "select * from balance_detail where compte = :noCompte limit 1")
    public Optional<BalanceDetail> queryBilan(Long noCompte); */
}
