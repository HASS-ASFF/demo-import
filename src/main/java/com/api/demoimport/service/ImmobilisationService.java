package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Immobilisation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImmobilisationService {

    void save(MultipartFile file, String date, String company_name);
    List<Immobilisation> getImmobilisations();
}
