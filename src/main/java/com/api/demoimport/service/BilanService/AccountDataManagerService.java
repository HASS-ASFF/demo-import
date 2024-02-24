package com.api.demoimport.service.BilanService;

import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;

import java.util.List;

public interface AccountDataManagerService {

    // PROCESS ACCOUNTS ( INITIALIZE / REGROUPING /  GETTING TOTAL / FILTERING )

    List<SubAccountActif> processAccountDataA(List<SubAccountActif> rawData, String n_class);
    List<SubAccountPassif> processAccountDataP(List<SubAccountPassif> rawData, String n_class);

    String extractPrefix(String accountNumber);

    Double GetTotalBrutAccountActif(List<SubAccountActif> accountDataMap);
    Double GetTotalAmortAccountActif(List<SubAccountActif> accountDataMap);

    Double GetTotalBrutAccountPassif(List<SubAccountPassif> accountData);
    Double GetTotalNetAccountPassif(List<SubAccountPassif> accountData);

    List<SubAccountActif> FilterAccountDataA(List<SubAccountActif> subAccountActifs,String mainAccount);
    List<SubAccountPassif> FilterAccountDataP(List<SubAccountPassif> subAccountPassifs,String mainAccount);
}
