package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.helper.ExcelHelper;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.repository.PlanComptableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelBalanceDetailService {

    @Autowired
    private BalanceDetailRepository repository;

    @Autowired
    private PlanComptableRepository planComptableRepository;

    public void save(MultipartFile file) {
        try {
            List<BalanceDetail> balanceDetails = ExcelHelper.excelToBalanceDetail(file.getInputStream(),planComptableRepository);
            repository.saveAll(balanceDetails);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }

    public List<BalanceDetail> getBalanceDetails() {
        return repository.findAll();
    }

    public  void setPlanComptableByNoCompte(BalanceDetail balanceDetail, Long noCompte) {
        PlanComptable planComptable = planComptableRepository.findById(noCompte)
                .orElseThrow(() -> new IllegalArgumentException("Aucun plan comptable trouvé pour le numéro de compte : " + noCompte));

        balanceDetail.setCompte(planComptable);
    }
}
