package com.api.demoimport.repository;

import com.api.demoimport.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for accessing and manipulating Balance entities.
 */
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    @Query(nativeQuery = true,value = "select * from balance where DATE_FORMAT(date,'%Y-%m-%d') = :date AND company_name = :company_name;")
    Optional<Balance> findByDateAndCompanyName(String date,String company_name);
}
