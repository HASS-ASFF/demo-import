package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.repository.PlanComptableRepository;
import com.api.demoimport.service.Implementation.ExcelHelperServiceImpl;
import com.api.demoimport.service.PlanComptableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class PlanComptableServiceImpl implements PlanComptableService {

    // Injection des dépendances
    @Autowired
    PlanComptableRepository repository;
    @Autowired
    ExcelHelperServiceImpl excelHelperServiceImpl;

    // Méthode pour sauvegarder les données d'un fichier Excel de type PlanComptable
    public void save(MultipartFile file) {
        try {
            // Convert data excel to PlanComptable object
            List<PlanComptable> planComptables = excelHelperServiceImpl.excelToPlanComptable(file.getInputStream());

            // save to db
            repository.saveAll(planComptables);

        } catch (IOException e) {
            // error reading the file
            throw new RuntimeException("fail to store excel data: ");
        } catch (ParseException e) {
            // error parsing the data
            throw new RuntimeException(e);
        }
    }

    // Get all PlanComptable data
    public List<PlanComptable> getPlanComptables() {
        return repository.findAll();
    }

    // Fetch for a data in PlanComptable with the account number
    public  Optional<PlanComptable> search(Long noCompte) {
        return repository.querySearchPlanComptable(noCompte);
    }
}
