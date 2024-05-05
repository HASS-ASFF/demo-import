package com.api.demoimport.repository;

import com.api.demoimport.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and manipulating Balance entities.
 */
public interface BalanceRepository extends JpaRepository<Balance,Long> {
}
