package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.BilanActif.BilanActif;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelBalanceDetailService {
    void save(MultipartFile file);
    List<BalanceDetail> getBalanceDetails();
    List<BilanActif> getClassTwo();
    List<Object[]> getClassThree();
    List<Object[]> getClassFive();
}
