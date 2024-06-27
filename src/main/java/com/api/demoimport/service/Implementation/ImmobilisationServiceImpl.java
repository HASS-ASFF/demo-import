package com.api.demoimport.service.Implementation;
import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.repository.ImmobilisationRepository;
import com.api.demoimport.service.ImmobilisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ImmobilisationServiceImpl implements ImmobilisationService {

    @Autowired
    ExcelHelperServiceImpl excelHelperServiceImpl;
    @Autowired
    ImmobilisationRepository immobilisationRepository;

    /**
     * Service implementation for managing Immobilisation objects, providing methods for saving,
     * creating, and finding immobilisations.
     * Uses ExcelHelperServiceImpl to convert Excel data to Immobilisation objects
     * and interacts with ImmobilisationRepository for saving them to database.
     */



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
    public Immobilisation createImmobilisation(Immobilisation immobilisation) {
        return immobilisationRepository.save(immobilisation);
    }

    @Override
    public List<Immobilisation> FindImmobilisation(String name, String date) {
        return immobilisationRepository.findImmobilisationByND(name,date);
    }

    @Override
    public Optional<Immobilisation> FindByID(Long id) {
        return immobilisationRepository.findById(id);
    }
}
