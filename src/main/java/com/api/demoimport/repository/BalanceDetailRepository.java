package com.api.demoimport.repository;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BalanceDetailRepository extends JpaRepository<BalanceDetail,Long> {

    // GETTING ACCOUNTS FROM BALANCE AND BY DATE

    // GET ACCOUNTS CLASS 1
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.credit_fex AS brut\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=1 AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY b.n_compte,b.label, b.credit_fex;")
    List<Object[]> getBilanC1(String dateBilan,String company_name);

    // GET ACCOUNTS CLASS 2
    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte,\n " +
            "b.label AS comptes,\n" +
            "COALESCE(SUM(b.debit_fex), 0) AS brut,\n" +
            "COALESCE(SUM(amort.credit_fex), 0) AS total_amort,\n" +
            "b.debit_fex - COALESCE(SUM(amort.credit_fex), 0) AS solde_net\n" +
            "FROM balance_detail b\n" +
            "LEFT JOIN balance_detail amort ON \n" +
            "    b.the_class = 2 AND\n" +
            "    amort.the_class = 2 AND\n" +
            "    amort.n_compte LIKE '28%' AND\n" +
            "    LEFT(b.n_compte, 3) = LEFT(CONCAT(REPLACE(amort.n_compte, '8', ''),'0'),3)\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class = 2 AND  b.n_compte NOT LIKE '28%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY b.n_compte,b.label, b.debit_fex;")
    List<Object[]> getBilanC2(String dateBilan,String company_name);

    // GET ACCOUNTS CLASS 3
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "b.debit_fex AS net\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class = 3 AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY \n" +
            "   b.n_compte,b.label, b.debit_fex;")
    List<Object[]> getBilanC3(String dateBilan,String company_name);

    // GET ACCOUNTS CLASS 4
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.credit_fex AS brut\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=4 AND  DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY b.n_compte,b.label, b.credit_fex;")
    List<Object[]> getBilanC4(String dateBilan,String company_name);

    // GET CLASS 5 ACTIF
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "b.debit_fex AS net\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class = 5 AND b.n_compte LIKE '51%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY  b.n_compte,b.label, b.debit_fex;")
    List<Object[]> getBilanC5A(String dateBilan,String company_name);

    // GET CLASS 5 PASSIF
    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.credit_fex AS brut\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=5 AND b.n_compte LIKE '55%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY b.n_compte,b.label, b.credit_fex;")
    List<Object[]> getBilanC5P(String dateBilan,String company_name);

}
