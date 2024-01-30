package com.api.demoimport.service;

import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.helper.ExcelHelper;
import com.api.demoimport.repository.PlanComptableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelPlanComptableService {
    @Autowired
    PlanComptableRepository repository;

    public void save(MultipartFile file) {
        try {
            List<PlanComptable> planComptables = ExcelHelper.excelToPlanComptable(file.getInputStream());
            repository.saveAll(planComptables);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }

    public List<PlanComptable> getPlanComptables() {
        return repository.findAll();
    }
}
