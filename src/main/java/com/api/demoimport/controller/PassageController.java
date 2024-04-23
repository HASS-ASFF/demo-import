package com.api.demoimport.controller;

import com.api.demoimport.dto.ServiceResponse;
import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.enums.AccountCategoryClass6;
import com.api.demoimport.enums.PassageCategory;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.service.Implementation.AccountDataManagerServiceImpl;
import com.api.demoimport.service.Implementation.BalanceDetailServiceImpl;
import com.api.demoimport.service.Implementation.PassageServiceImpl;
import com.api.demoimport.service.PassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PassageController {

    @Autowired
    PassageServiceImpl passageService;

    @Autowired
    BalanceDetailRepository balanceDetailRepository;

    @Autowired
    BalanceDetailServiceImpl balanceDetailService;

    @Autowired
    AccountDataManagerServiceImpl accountDataManagerService;

    @PutMapping("/updatePassage/{name}/{date}")
    public ResponseEntity<Object> modifyPassage(@PathVariable String name,@PathVariable String date,@RequestBody Passage passage) {
        Optional<Passage> passageData = passageService.findByNameAndDate(name,date);

        if (passageData.isPresent()) {
            Passage existingPassage = passageData.get();
            // Mettre à jour les propriétés du passage existante avec les nouvelles valeurs
            existingPassage.setAmountPlus(passage.getAmountPlus());
            existingPassage.setAmountMinus(passage.getAmountMinus());

            // Enregistrer le passage mise à jour dans la base de données
            Passage updatedPassage = passageService.createPassage(existingPassage);

            return ResponseEntity.ok("Passage modifiée avec succès !");
        } else {
            Passage newPassage =new Passage();
            newPassage.setName(passage.getName());
            newPassage.setAmountPlus(passage.getAmountPlus());
            newPassage.setAmountMinus(passage.getAmountMinus());
            newPassage.setDate(passage.getDate());

            Passage updatedPassage = passageService.createPassage(newPassage);

            return  ResponseEntity.ok("Nouveau passage ajouté avec succès !");
        }
    }


    @RequestMapping(value = "/passagesFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> passagesFilter(@RequestParam("date") String dateString) {
        Map<String, Object> responseMap = new HashMap<>();

        List<Passage> passages_db = passageService.findByDate(dateString);
        List<Passage> passages_final = passageService.processAccountData(passages_db);

        List<List<Passage>> parts = new ArrayList<>();
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_COMPTABLE.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.REINTEGRATIONS_FISCALES.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.DEDUCTIONS_FISCALES.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_BRUT_FISCAL.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.REPORTS_DEFICITAIRES_IMPUTES.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_FISCAL.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.CUMUL_DES_AMORTISSEMENTS_FISCALEMENT_DIFFERES.getMain_name()));
        parts.add(passageService.FilterPassages(passages_final, PassageCategory.CUMUL_DES_DEFICITS_FISCAUX_RESTANT_A_REPORTER.getMain_name()));

        responseMap.put("status", "success");
        responseMap.put("data", parts);

        return ResponseEntity.ok().body(responseMap);
    }

    @RequestMapping(value = "/passagesImmoFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> passagesImmoFilter(@RequestParam("date") String dateBilan){
        Map<String, Object> responseMap = new HashMap<>();
        List<SubAccountActif> passageImmo = passageService.findPassageImmo(dateBilan,"AL MORAFIQ");
        List<SubAccountActif> passageImmo_final = accountDataManagerService.processAccountDataA(passageImmo,"2");
        List<List<SubAccountActif>> parts = new ArrayList<>();
        parts.add(accountDataManagerService.FilterAccountDataA(passageImmo_final, AccountCategoryClass2.IMMOBILISATION_NON_VALEURS.getLabel()));
        parts.add(accountDataManagerService.FilterAccountDataA(passageImmo_final, AccountCategoryClass2.IMMOBILISATION_INCORPORELLES.getLabel()));
        parts.add(accountDataManagerService.FilterAccountDataA(passageImmo_final, AccountCategoryClass2.IMMOBILISATION_CORPORELLES.getLabel()));

        responseMap.put("status", "success");
        responseMap.put("data", parts);

        return ResponseEntity.ok().body(responseMap);
    }

    @RequestMapping(value="/passagesESGFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> passagesESGFilter(@RequestParam("date") String dateBilan){
        Map<String, Object> responseMap = new HashMap<>();
        // TO DO
        return ResponseEntity.ok().body(responseMap);
    }

    @RequestMapping(value="/passagesCPCFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> passagesCPCFilter(@RequestParam("date") String dateBilan){
        Map<String, Object> responseMap = new HashMap<>();
        // TO DO
        return ResponseEntity.ok().body(responseMap);
    }

}





    /*@RequestMapping(value="/edit/{id}")
    public ModelAndView showEditPassagePage(@PathVariable(name = "id") Long id){
        ModelAndView mav = new ModelAndView("new");
        Passage passage = passageService.getById(id);
        mav.addObject("passage",passage);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deletePassage(@PathVariable(name = "id") Long id){
        return null;
    }*/


// Méthode pour vérifier si les données sont dans la base de données
    /*private boolean isDataInDatabase(String dateString) {
        return balanceDetailRepository.getCPCC6(dateString, "AL MORAFIQ") != null ||
                balanceDetailRepository.getCPCC7(dateString, "AL MORAFIQ") != null;
    }*/

    /*// Méthode pour créer un Passage à partir d'un montant
    private Passage createPassageFromAmount(String label, double amount) {
        Passage passage = new Passage();
        passage.setName(label);

        if (label.contains("Perte nette") || label.contains("NON COURANTES")) {
            passage.setAmountMinus(Math.abs(amount));
        } else {
            passage.setAmountPlus(amount);
        }

        return passage;
    }*/

/* @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Object> addPassage(@RequestBody Passage passage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("data", passageService.createPassage(passage));
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }*/

   /* @RequestMapping(value = "/getPassagesByDate", method = RequestMethod.GET)
    public ResponseEntity<Object> getPassagesByDate(@RequestParam("date") String dateString) {
        Map<String, Object> responseMap = new HashMap<>();

        if(isDataInDatabase(dateString)){
            // CLASS SIX
            List<SubAccountCPC> ClassSix = balanceDetailService.getClassSix(dateString,"AL MORAFIQ");
            List<SubAccountCPC> FullClassSix = accountDataManagerService.
                    processAccountDataCPC(ClassSix,"6");

            // CLASS SEVEN
            List<SubAccountCPC> ClassSeven = balanceDetailService.getClassSeven(dateString,"AL MORAFIQ");
            List<SubAccountCPC> FullClassSeven = accountDataManagerService.
                    processAccountDataCPC(ClassSeven,"7");

            // FOR CHARGES NON COURANTES
            List<SubAccountCPC> dataset6 = accountDataManagerService.
                    FilterAccountDataCPC(FullClassSix, AccountCategoryClass6.CHARGES_NON_COURANTES.getLabel());

            // FOR TOTAL BENEFICE/PERTE AND CHARGES NON COURANTES
            Double totalListI = accountDataManagerService.GetTotalBrutCPC(FullClassSix);
            totalListI += accountDataManagerService.GetTotalBrutCPC(FullClassSeven);

            Double totalListII = accountDataManagerService.GetTotalBrutCPC(dataset6);

            List<Passage> passage_db = new ArrayList<>();
            if(totalListI > 0){
                Passage passageBenefice = createPassageFromAmount("Bénéfice net",totalListI);
                passage_db.add(passageBenefice);
            }else{
                Passage passagePerte = createPassageFromAmount("Perte nette",totalListI);
                passage_db.add(passagePerte);
            }
            Passage passageCharge = createPassageFromAmount("NON COURANTES",totalListII);

            passage_db.add(passageCharge);

            List<Passage> passages_final = passageService.processAccountData(passage_db);

            List<List<Passage>> parts = new ArrayList<>();
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_COMPTABLE.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.REINTEGRATIONS_FISCALES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.DEDUCTIONS_FISCALES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_BRUT_FISCAL.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.REPORTS_DEFICITAIRES_IMPUTES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_FISCAL.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.CUMUL_DES_AMORTISSEMENTS_FISCALEMENT_DIFFERES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.CUMUL_DES_DEFICITS_FISCAUX_RESTANT_A_REPORTER.getMain_name()));

            responseMap.put("status", "success");
            responseMap.put("data", parts);

        }
        else{
            List<Passage> passages_db = passageService.findPassages(dateString);
            List<Passage> passages_final = passageService.processAccountData(passages_db);

            List<List<Passage>> parts = new ArrayList<>();
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_COMPTABLE.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.REINTEGRATIONS_FISCALES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.DEDUCTIONS_FISCALES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_BRUT_FISCAL.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.REPORTS_DEFICITAIRES_IMPUTES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_FISCAL.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.CUMUL_DES_AMORTISSEMENTS_FISCALEMENT_DIFFERES.getMain_name()));
            parts.add(passageService.FilterPassages(passages_final, PassageCategory.CUMUL_DES_DEFICITS_FISCAUX_RESTANT_A_REPORTER.getMain_name()));

            responseMap.put("status", "success");
            responseMap.put("data", parts);

        }

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }*/