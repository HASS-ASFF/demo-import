package com.api.demoimport.service;

import com.api.demoimport.entity.BilanAndCPC.SubAccountActif;
import com.api.demoimport.entity.BilanAndCPC.SubAccountCPC;
import com.api.demoimport.entity.BilanAndCPC.SubAccountPassif;

import java.util.List;

public interface AccountDataManagerService {

    // PROCESS ACCOUNTS ( INITIALIZE / REGROUPING /  GETTING TOTAL / FILTERING )

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
}
