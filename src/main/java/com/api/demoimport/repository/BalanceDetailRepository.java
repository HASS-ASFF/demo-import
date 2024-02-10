package com.api.demoimport.repository;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BalanceDetailRepository extends JpaRepository<BalanceDetail,Long> {

    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte,\n " +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "COALESCE(SUM(amort.credit_fex), 0) AS total_amort,\n" +
            "b.debit_fex - COALESCE(SUM(amort.credit_fex), 0) AS solde_net\n" +
            "FROM balance_detail b\n" +
            "LEFT JOIN balance_detail amort ON \n" +
            "    b.the_class = 2 AND\n" +
            "    amort.the_class = 2 AND\n" +
            "    amort.n_compte LIKE '28%' AND\n" +
            "    LEFT(b.n_compte, 3) = LEFT(CONCAT(REPLACE(amort.n_compte, '8', ''),'0'),3)\n" +
            "WHERE b.the_class = 2 AND  b.n_compte NOT LIKE '28%'\n" +
            "GROUP BY b.n_compte,b.label, b.debit_fex;")
    List<Object[]> getBilanC2();


    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "b.debit_fex AS net\n" +
            "FROM balance_detail b\n" +
            "WHERE b.the_class = 3\n" +
            "GROUP BY \n" +
            "   b.n_compte,b.label, b.debit_fex;")
    List<Object[]> getBilanC3();

    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "b.debit_fex AS net\n" +
            "FROM balance_detail b\n" +
            "WHERE b.the_class = 5\n" +
            "GROUP BY  b.n_compte,b.label, b.debit_fex;")
    List<Object[]> getBilanC5();
}
