package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.repository.BalanceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class ExcelBalanceDetailServiceImpl implements ExcelBalanceDetailService{

    // Injection des dépendances
    @Autowired
    private BalanceDetailRepository repository;

    @Autowired
    private ExcelHelperServiceImpl excelHelperServiceImpl;

    // Méthode pour sauvegarder les données d'un fichier Excel de type BalanceDetail
    public void save(MultipartFile file) {
        try {
            List<BalanceDetail> balanceDetails = excelHelperServiceImpl.excelToBalanceDetail(file.getInputStream());
            repository.saveAll(balanceDetails);
    // Gestion des exceptions en cas d'échec de la lecture du fichier ou conversion
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }


    // Méthode pour récupérer tous les balance detail
    public List<BalanceDetail> getBalanceDetails() {
        return repository.findAll();
    }

    public List<Object[]> getClassTwo(){
        return repository.getBilanC2();
    }
    public List<Object[]> getClassThree(){return repository.getBilanC3();}

    public List<Object[]> getClassFive(){return repository.getBilanC5();}
}
