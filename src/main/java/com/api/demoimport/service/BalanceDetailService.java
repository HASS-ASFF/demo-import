package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface BalanceDetailService {

    // Getting different classes from BalanceDetail & converting it to SubAccount object

    void save(MultipartFile file, String date);
    List<BalanceDetail> getBalanceDetails();
    List<SubAccountPassif> getClassOne(String date);
    List<SubAccountActif> getClassTwo(String date);
    List<SubAccountActif> getClassThree(String date);
    List<SubAccountPassif> getClassFour(String date);
    List<SubAccountActif> getClassFiveActif(String date);

    List<SubAccountPassif> getClassFivePassif(String date);

    List<SubAccountActif> ConvertToBilanActif(List<Object[]> resultsrequest);
    List<SubAccountPassif> ConvertToBilanPassif(List<Object[]> resultsrequest);

    void regroupClassesA(List<SubAccountActif> bilanActifs);
    void regroupClassesP(List<SubAccountPassif> bilanPassif);

    String getMainAccount(String n_compte);
}
