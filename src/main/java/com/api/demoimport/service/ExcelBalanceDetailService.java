package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.Bilan;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelBalanceDetailService {
    void save(MultipartFile file);
    List<BalanceDetail> getBalanceDetails();
    List<Bilan> getClassOne();
    List<Bilan> getClassTwo();
    List<Bilan> getClassThree();
    List<Bilan> getClassFour();
    List<Bilan> getClassFiveActif();

    List<Bilan> getClassFivePassif();
}
