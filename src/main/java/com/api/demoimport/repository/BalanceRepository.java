package com.api.demoimport.repository;

import com.api.demoimport.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance,Long> {
}
