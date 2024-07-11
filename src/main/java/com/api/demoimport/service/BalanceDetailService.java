package com.api.demoimport.service;

import com.api.demoimport.dto.Tvadto;
import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BalanceDetailService {

    /**
     * Interface defining service operations for handling balance details.
     * Methods include saving balance details from a file, retrieving balance details,
     * and converting them into various subaccount objects for the creation of the various balance sheet tables
     */

    void save(MultipartFile file, String date,String company_name);
    CompletableFuture<List<SubAccountPassif>> getClassOne(String date, String company_name);
    CompletableFuture<List<SubAccountActif>> getClassTwo(String date,String company_name);
    CompletableFuture<List<SubAccountActif>> getClassThree(String date,String company_name);
    CompletableFuture<List<SubAccountPassif>> getClassFour(String date,String company_name);
    CompletableFuture<List<SubAccountActif>> getClassFiveActif(String date,String company_name);

    CompletableFuture<List<SubAccountPassif>> getClassFivePassif(String date,String company_name);

    CompletableFuture<List<SubAccountCPC>> getClassSix(String date,String company_name);
    CompletableFuture<List<SubAccountCPC>> getClassSeven(String date,String company_name);

    CompletableFuture<List<Tvadto>> getTvaData(String date,String company_name);

    List<SubAccountActif> ConvertToBilanActif(List<Object[]> resultsrequest);
    List<SubAccountPassif> ConvertToBilanPassif(List<Object[]> resultsrequest);

    List<SubAccountCPC> ConvertToCPC(List<Object[]> resultsrequest);

    void regroupClassesA(List<SubAccountActif> bilanActifs);
    void regroupClassesP(List<SubAccountPassif> bilanPassif);

    void regroupClassesCPC(List<SubAccountCPC> cpcAccount);

    String getMainAccount(String n_compte);
    Optional<Double> getMaterielInfoValue(String date,String compny_name);

    List<Double> getDetailStockValues(String date,String company_name);
}
