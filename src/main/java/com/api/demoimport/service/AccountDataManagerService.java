package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface AccountDataManagerService {

    /**
     * Interface defining service operations for managing account data (ACTIF / PASSIF / CPC ).
     * Methods include processing different types of account data (initialization, regrouping, total retrieval, filtering),
     * and extracting prefixes from account numbers.
     */
    List<SubAccountActif> processAccountDataA(List<SubAccountActif> rawData, String n_class);
    List<SubAccountPassif> processAccountDataP(List<SubAccountPassif> rawData, String n_class);

    List<SubAccountCPC> processAccountDataCPC(List<SubAccountCPC> rawData, String n_class);

    String extractPrefix(String accountNumber);

    Double GetTotalBrutAccountActif(List<SubAccountActif> accountDataMap);
    Double GetTotalAmortAccountActif(List<SubAccountActif> accountDataMap);

    Double GetTotalBrutAccountPassif(List<SubAccountPassif> accountData);
    Double GetTotalNetAccountPassif(List<SubAccountPassif> accountData);

    List<SubAccountActif> FilterAccountDataA(List<SubAccountActif> subAccountActifs,String mainAccount);
    List<SubAccountPassif> FilterAccountDataP(List<SubAccountPassif> subAccountPassifs,String mainAccount);

    List<SubAccountCPC> FilterAccountDataCPC(List<SubAccountCPC> subAccountCPCS, String mainAccount);

    void updateTotalBrutCPC(List<SubAccountCPC> datasetCurrent);
/*
    void updateTotalBrutCPCP(List<SubAccountCPC> datasetPrevious);
*/

    <T extends Updatable> void updateExerciceP(List<T> datasetPrevious, List<T> datasetCurrent);
}
