package com.api.demoimport.controller;

import com.api.demoimport.dto.ServiceResponse;
import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Object> addPassage(@RequestBody Passage passage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("data", passageService.createPassage(passage));
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPassagesByDate", method = RequestMethod.GET)
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
    }

    @PostMapping("/savePassage")
    public ResponseEntity<Object> savePassage(@RequestBody Passage passage) {
        Map<String, Object> responseMap = new HashMap<>();

        // Vérifier si le passage existe déjà en fonction de certains critères, par exemple, le nom et la date
        Passage existingPassage = passageService.findByNameAndDate(passage.getName(), passage.getDate());

        if (existingPassage != null) {
            // Mettre à jour les valeurs existantes du passage avec les nouvelles valeurs
            existingPassage.setAmountPlus(passage.getAmountPlus());
            existingPassage.setAmountMinus(passage.getAmountMinus());

            // Enregistrer le passage mis à jour dans la base de données
            Passage updatedPassage = passageService.updatePassage(existingPassage);

            if (updatedPassage != null) {
                responseMap.put("status", "success");
                responseMap.put("data", updatedPassage);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "Échec de la mise à jour du passage.");
                return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Si le passage n'existe pas encore, l'enregistrer comme nouveau passage
            Passage savedPassage = passageService.createPassage(passage);

            if (savedPassage != null) {
                responseMap.put("status", "success");
                responseMap.put("data", savedPassage);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "Échec de l'enregistrement du passage.");
                return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    @RequestMapping(value = "/passagesFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> passagesFilter(@RequestParam("date") String dateString) {
        Map<String, Object> responseMap = new HashMap<>();
        List<Passage> passages_db = passageService.findPassages(dateString);
        List<Passage> passages_final = passageService.processAccountData(passages_db);
        //System.out.println(passages_final);
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


    // Méthode pour vérifier si les données sont dans la base de données
    private boolean isDataInDatabase(String dateString) {
        return balanceDetailRepository.getCPCC6(dateString, "AL MORAFIQ") != null ||
                balanceDetailRepository.getCPCC7(dateString, "AL MORAFIQ") != null;
    }

    // Méthode pour créer un Passage à partir d'un montant
    private Passage createPassageFromAmount(String label, double amount) {
        Passage passage = new Passage();
        passage.setName(label);

        if (label.contains("Perte nette") || label.contains("NON COURANTES")) {
            passage.setAmountMinus(Math.abs(amount));
        } else {
            passage.setAmountPlus(amount);
        }

        return passage;
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
