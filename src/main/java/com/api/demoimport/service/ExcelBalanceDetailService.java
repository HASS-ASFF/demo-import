package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.repository.PlanComptableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class ExcelBalanceDetailService {

    @Autowired
    private BalanceDetailRepository repository;

    @Autowired
    private ExcelHelperService excelHelperService;

    public void save(MultipartFile file) {
        try {
            List<BalanceDetail> balanceDetails = excelHelperService.excelToBalanceDetail(file.getInputStream());
            repository.saveAll(balanceDetails);
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }

    public List<BalanceDetail> getBalanceDetails() {
        return repository.findAll();
    }
}
