package com.api.demoimport.service;

import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.repository.PlanComptableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelPlanComptableService {
    @Autowired
    PlanComptableRepository repository;
    @Autowired
    ExcelHelperService excelHelperService;

    public void save(MultipartFile file) {
        try {
            List<PlanComptable> planComptables = excelHelperService.excelToPlanComptable(file.getInputStream());
            repository.saveAll(planComptables);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: ");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlanComptable> getPlanComptables() {
        return repository.findAll();
    }

    public  Optional<PlanComptable> search(Long noCompte) {
        return repository.querySearchPlanComptable(noCompte);
    }
}
