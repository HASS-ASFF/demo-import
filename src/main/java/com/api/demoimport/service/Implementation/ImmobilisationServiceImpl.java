package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.repository.ImmobilisationRepository;
import com.api.demoimport.service.ImmobilisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class ImmobilisationServiceImpl implements ImmobilisationService {

    @Autowired
    ExcelHelperServiceImpl excelHelperServiceImpl;
    @Autowired
    ImmobilisationRepository immobilisationRepository;

    @Override
    public void save(MultipartFile file, String date, String company_name) {
        try {
            List<Immobilisation> immobilisations = excelHelperServiceImpl.excelToImmobilisation(file.getInputStream(),date,company_name);
            immobilisationRepository.saveAll(immobilisations);
            // In case we have  parsing or I/O error
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("fail to store excel data: ");
        }
    }

    @Override
    public List<Immobilisation> getImmobilisations() {
        return null;
    }
}
