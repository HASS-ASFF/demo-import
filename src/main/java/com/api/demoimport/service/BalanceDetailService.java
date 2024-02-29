package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.BilanAndCPC.SubAccountActif;
import com.api.demoimport.entity.BilanAndCPC.SubAccountCPC;
import com.api.demoimport.entity.BilanAndCPC.SubAccountPassif;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BalanceDetailService {

    // Getting different classes from BalanceDetail & converting it to SubAccount object

    void save(MultipartFile file, String date,String company_name);
    List<BalanceDetail> getBalanceDetails();
    List<SubAccountPassif> getClassOne(String date,String company_name);
    List<SubAccountActif> getClassTwo(String date,String company_name);
    List<SubAccountActif> getClassThree(String date,String company_name);
    List<SubAccountPassif> getClassFour(String date,String company_name);
    List<SubAccountActif> getClassFiveActif(String date,String company_name);

    List<SubAccountPassif> getClassFivePassif(String date,String company_name);

    List<SubAccountCPC> getClassSix(String date,String company_name);
    List<SubAccountCPC> getClassSeven(String date,String company_name);

    List<SubAccountActif> ConvertToBilanActif(List<Object[]> resultsrequest);
    List<SubAccountPassif> ConvertToBilanPassif(List<Object[]> resultsrequest);

    List<SubAccountCPC> ConvertToCPC(List<Object[]> resultsrequest);

    void regroupClassesA(List<SubAccountActif> bilanActifs);
    void regroupClassesP(List<SubAccountPassif> bilanPassif);

    void regroupClassesCPC(List<SubAccountCPC> cpcAccount);

    String getMainAccount(String n_compte);
}
