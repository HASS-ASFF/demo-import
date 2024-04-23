package com.api.demoimport.controller;

import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.service.ImmobilisationService;
import com.api.demoimport.service.Implementation.ImmobilisationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ImmobilisationController {
    @Autowired
    ImmobilisationServiceImpl immobilisationService;

    @RequestMapping(value = "/immobilisationFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> passagesImmoFilter(@RequestParam("date") String dateBilan){
        Map<String, Object> responseMap = new HashMap<>();
        List<Immobilisation> immobilisations = immobilisationService.FindImmobilisation("AL MORAFIQ",dateBilan);

        responseMap.put("status", "success");
        responseMap.put("data", immobilisations);

        return ResponseEntity.ok().body(responseMap);
    }

    @PutMapping("/updateImmobilisation/{id}")
    public ResponseEntity<String> modifyImmobilisation(@PathVariable Long id, @RequestBody Immobilisation immobilisation) {
        Optional<Immobilisation> immobilisationData = immobilisationService.FindByID(id);

        if (immobilisationData.isPresent()) {
            Immobilisation existingImmobilisation = immobilisationData.get();
            // Mettre à jour les propriétés de l'immobilisation existante avec les nouvelles valeurs
            existingImmobilisation.setDateAquisition(immobilisation.getDateAquisition());
            existingImmobilisation.setPrixAquisition(immobilisation.getPrixAquisition());
            existingImmobilisation.setCoutDeRevient(immobilisation.getCoutDeRevient());
            existingImmobilisation.setAmortAnterieur(immobilisation.getAmortAnterieur());
            existingImmobilisation.setTaux_amort(immobilisation.getTaux_amort());
            existingImmobilisation.setAmortDeduitBenefice(immobilisation.getAmortDeduitBenefice());
            existingImmobilisation.setDea(immobilisation.getDea());
            existingImmobilisation.setDeaGlobal(immobilisation.getDeaGlobal());

            // Mettre à jour d'autres propriétés selon les besoins

            // Enregistrer l'immobilisation mise à jour dans la base de données
            Immobilisation updatedImmobilisation = immobilisationService.createImmobilisation(existingImmobilisation);

            return ResponseEntity.ok("Immobilisation modifiée avec succès !");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
