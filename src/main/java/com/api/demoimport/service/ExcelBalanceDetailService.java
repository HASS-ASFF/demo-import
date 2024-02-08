package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelBalanceDetailService {
    void save(MultipartFile file);
    List<BalanceDetail> getBalanceDetails();
    List<Object[]> getClassTwo();
    List<Object[]> getClassThree();
    List<Object[]> getClassFive();
}
