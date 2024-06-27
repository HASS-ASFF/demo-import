package com.api.demoimport.repository;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating BalanceDetail entities.
 */

public interface BalanceDetailRepository extends JpaRepository<BalanceDetail,Long> {

    // GETTING ACCOUNTS FROM BALANCE AND BY DATE

    // GET ACCOUNTS CLASS 1
    // IN CASE IF WE HAVE DEBIT ADD minus (-)
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte, b.label AS comptes,\n" +
            "CASE \n" +
            "   WHEN b.credit_fex = 0 AND b.n_compte LIKE '116%' THEN -1 * b.debit_fex \n" +
            "   ELSE b.credit_fex\n" +
            "END AS brut\n" +
            "            FROM balance_detail b\n" +
            "            JOIN balance ON balance.id = b.balance_id\n" +
            "            WHERE b.the_class=1 AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "            AND company_name = :company_name\n" +
            "            GROUP BY b.n_compte,b.label, b.credit_fex, b.debit_fex;")
    List<Object[]> getBilanC1(String dateBilan,String company_name);

    // GET ACCOUNTS CLASS 2
    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte,\n" +
            "            b.label AS comptes,\n" +
            "            b.debit_fex AS brut,\n" +
            "            coalesce(amort.credit_fex,0) AS total_amort,\n" +
            "            b.debit_dex AS netn\n" +
            "            FROM balance_detail b\n" +
            "            LEFT JOIN balance_detail amort ON \n" +
            "                b.the_class = 2 AND\n" +
            "                amort.the_class = 2 AND\n" +
            "                amort.n_compte LIKE '28%' AND\n" +
            "                LEFT(b.n_compte, 3) = LEFT(CONCAT(REPLACE(amort.n_compte, '8', ''),'0'),3)\n" +
            "            JOIN balance ON balance.id = b.balance_id\n" +
            "            WHERE b.the_class = 2 AND  b.n_compte NOT LIKE '28%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "            AND company_name = :company_name\n" +
            "            GROUP BY b.n_compte,b.label, b.debit_fex,amort.credit_fex, b.debit_dex;")
    List<Object[]> getBilanC2(String dateBilan,String company_name);

    // GET PASSAGES IMMOBILISATIONS
    @Query(nativeQuery = true, value = "SELECT bd.n_compte AS num_compte, bd.label AS comptes,\n" +
            "                       COALESCE(SUM(bd.debit_fex), 0) AS brut\n" +
            "                        FROM balance_detail bd\n" +
            "                        JOIN balance b ON b.id = bd.balance_id \n" +
            "                        WHERE bd.the_class = 2 AND b.company_name = :company_name AND bd.n_compte REGEXP  '2[123].*'\n" +
            "                        AND date_format(b.date, '%Y-%m-%d') = :dateBilan" +
            "                        GROUP BY bd.n_compte,bd.label, bd.debit_fex;")
    List<Object[]> getPassageImmo(String dateBilan,String company_name);


    // GET ACCOUNTS CLASS 3
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "b.debit_fex AS net,\n" +
            "b.debit_dex AS netn\n"+
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class = 3 AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY \n" +
            "   b.n_compte,b.label, b.debit_fex, b.debit_dex;")
    List<Object[]> getBilanC3(String dateBilan,String company_name);

    // GET ACCOUNTS CLASS 4
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.credit_fex AS brut,\n" +
            "b.credit_dex AS brutp\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=4 AND  DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY b.n_compte,b.label, b.credit_fex, b.credit_dex;")
    List<Object[]> getBilanC4(String dateBilan,String company_name);

    // GET CLASS 5 ACTIF
    @Query(nativeQuery = true, value ="SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.debit_fex AS brut,\n" +
            "b.debit_fex AS net,\n" +
            "b.debit_dex AS netn\n"+
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class = 5 AND b.n_compte LIKE '51%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY  b.n_compte,b.label, b.debit_fex, b.debit_dex;")
    List<Object[]> getBilanC5A(String dateBilan,String company_name);

    // GET CLASS 5 PASSIF
    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte,\n" +
            "b.label AS comptes,\n" +
            "b.credit_fex AS brut,\n" +
            "b.credit_dex AS brutp\n"+
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=5 AND b.n_compte LIKE '55%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan\n" +
            "AND company_name = :company_name\n"+
            "GROUP BY b.n_compte,b.label, b.credit_fex, b.credit_dex;")
    List<Object[]> getBilanC5P(String dateBilan,String company_name);

    // GET CLASS 6
    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte, b.label AS comptes,\n" +
            "CASE \n"+
            "WHEN b.debit_fex = 0 THEN -b.credit_fex \n" +
            "ELSE b.debit_fex \n" +
            "END AS brut\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=6 AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :date\n" +
            "AND company_name = :company_name \n" +
            "GROUP BY b.n_compte,b.label, b.debit_fex, b.credit_fex;")
    List<Object []> getCPCC6(String date,String company_name);

    // GET CLASS 7
    @Query(nativeQuery = true, value = "SELECT b.n_compte AS num_compte,\n"+
            "b.label AS comptes,\n"+
            "b.credit_fex AS brut\n"+
            "FROM balance_detail b\n"+
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.the_class=7 AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :date \n" +
            "AND company_name = :company_name \n"+
            "GROUP BY b.n_compte,b.label, b.credit_fex;")
    List<Object []> getCPCC7(String date,String company_name);

    @Query(nativeQuery = true, value = "SELECT ifnull(b.credit_dex,0) AS credit_dex,\n" +
            "ifnull(b.credit_ex,0) AS credit_ex, \n" +
            "ifnull(b.debit_ex,0) AS debit_ex \n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.n_compte LIKE '44550000'\n" +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :date\n" +
            "AND company_name = :company_name")
    List<Object []> getTvaValues(String date,String company_name);

    @Query(nativeQuery = true, value = "SELECT ifnull(b.debit_dex,0) AS debit_dex, \n" +
            "ifnull(b.debit_ex,0) AS debit_ex,\n" +
            "ifnull(b.credit_ex,0) AS credit_ex \n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.n_compte LIKE '34552000'\n" +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :date\n" +
            "AND company_name = :company_name")
    List<Object []> getTvaSCValues(String date,String company_name);

    @Query(nativeQuery = true, value = "SELECT ifnull(b.debit_dex,0) AS debit_dex, \n" +
            "ifnull(b.debit_ex,0) AS debit_ex, \n" +
            "ifnull(b.credit_ex,0) AS credit_ex\n" +
            "FROM balance_detail b\n" +
            "JOIN balance ON balance.id = b.balance_id\n" +
            "WHERE b.n_compte LIKE '34551000'\n" +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :date\n" +
            "AND balance.company_name = :company_name")
    List<Object []> getTvaSIValues(String date,String company_name);

    @Query(nativeQuery = true,value = "SELECT ifnull(b.debit_fex,0) AS brut\n" +
            "            FROM balance_detail b\n" +
            "            JOIN balance ON balance.id = b.balance_id\n" +
            "            WHERE b.the_class = 2 AND  b.n_compte LIKE '2355%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :date\n" +
            "            AND company_name = :company_name\n")
    Optional<Double>  getMaterielInfo(String date,String company_name);

    // GET V1 to V3 (All 311 & 312 accounts)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE (b.n_compte LIKE '311%' OR b.n_compte LIKE '312%') " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV1toV3(String dateBilan, String company_name);

    // GET V4 (311 Marchandises + 3122 Matières et fournitures consommables)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '311%' OR b.n_compte LIKE '3122%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV4(String dateBilan, String company_name);

    // GET V5 (3121 Matières premières)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '3121%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV5(String dateBilan, String company_name);

    // GET V6 (3122 Matières consommables)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '3122%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV6(String dateBilan, String company_name);

    // GET V7 (31225 Fournitures d'atelier et d'usine)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31225%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV7(String dateBilan, String company_name);

    // GET V8 (31223 Combustibles)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31223%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV8(String dateBilan, String company_name);

    // GET V9 (3123 Emballages)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '3123%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV9(String dateBilan, String company_name);

    // GET V10 (31232 Emballages récupérables non identifiables)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31232%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV10(String dateBilan, String company_name);

    // GET V11 (31231 Emballages perdus)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31231%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV11(String dateBilan, String company_name);

    // GET V12 (312 Identifiable Emballages)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '312%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV12(String dateBilan, String company_name);

    // GET V13 (313 Produits en cours)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '313%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV13(String dateBilan, String company_name);

    // GET V14 (31342 Etudes en cours)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31342%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV14(String dateBilan, String company_name);

    // GET V15 (31341 Travaux en cours)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31341%' AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV15(String dateBilan, String company_name);

    // GET V16 (3134 Services en cours)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '3134%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV16(String dateBilan, String company_name);

    // GET V17 (315 Produits finis)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '315%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV17(String dateBilan, String company_name);

    // GET V18 (3151 Produits finis (groupe A))
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '3151%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV18(String dateBilan, String company_name);

    // GET V19 (31451 Déchets)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31451%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV19(String dateBilan, String company_name);

    // GET V20 (31452 Rebuts)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31452%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV20(String dateBilan, String company_name);

    // GET V21 (31453 Matières de récupération)
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(b.debit_fex), 0) AS brut " +
            "FROM balance_detail b " +
            "JOIN balance ON balance.id = b.balance_id " +
            "WHERE b.n_compte LIKE '31453%' " +
            "AND DATE_FORMAT(balance.date, '%Y-%m-%d') = :dateBilan " +
            "AND company_name = :company_name")
    Optional<Double> getV21(String dateBilan, String company_name);

}
