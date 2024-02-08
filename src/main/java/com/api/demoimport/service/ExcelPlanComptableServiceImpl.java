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
public class ExcelPlanComptableServiceImpl implements ExcelPlanComptableService{

    // Injection des dépendances
    @Autowired
    PlanComptableRepository repository;
    @Autowired
    ExcelHelperServiceImpl excelHelperServiceImpl;

    // Méthode pour sauvegarder les données d'un fichier Excel de type PlanComptable
    public void save(MultipartFile file) {
        try {
            // Conversion du fichier Excel en une liste d'objets PlanComptable
            List<PlanComptable> planComptables = excelHelperServiceImpl.excelToPlanComptable(file.getInputStream());

            // Sauvegarde des objets PlanComptable dans la base de données
            repository.saveAll(planComptables);

        } catch (IOException e) {
            // Gestion des exceptions en cas d'échec de la lecture du fichier
            throw new RuntimeException("fail to store excel data: ");
        } catch (ParseException e) {
            // Gestion des exceptions en cas d'erreur de conversion
            throw new RuntimeException(e);
        }
    }

    // Méthode pour récupérer tous les plans comptables
    public List<PlanComptable> getPlanComptables() {
        return repository.findAll();
    }

    // Méthode pour rechercher un plan comptable par numéro de compte
    public  Optional<PlanComptable> search(Long noCompte) {
        return repository.querySearchPlanComptable(noCompte);
    }
}
