package com.api.demoimport.controller;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.entity.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.enums.*;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.service.Implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BilanController {

    @Autowired
    PassageServiceImpl passageService;

    @Autowired
    BalanceDetailRepository balanceDetailRepository;

    @Autowired
    BalanceDetailServiceImpl balanceDetailService;

    @Autowired
    AccountDataManagerServiceImpl accountDataManagerService;

    @Autowired
    EsgServiceImpl esgService;

    @Autowired
    DetailCPCServiceImpl detailCPCService;

    /**
     * Controller class for handling requests related to passages.
     * Provides endpoints for modifying passages, filtering passages by date, and getting data for immo, ESG, and detailCPC.
     */

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
    public ResponseEntity<Map<String, Object>> passagesFilter(@RequestParam("date") String dateString) throws ParseException {
        Map<String, Object> responseMap = new HashMap<>();

        List<Passage> passages_db = passageService.findByDate(dateString);
        List<Passage> passages_list = new ArrayList<>();
        List<Passage> passages_final ;


        // Check if we have passage in db or get values from balance
        if(passages_db.isEmpty()){
            // FROM BALANCE
            List<SubAccountCPC> ClassSix = balanceDetailService.getClassSix(dateString,"AL MORAFIQ");
            List<SubAccountCPC> FullClassSix = accountDataManagerService.
                    processAccountDataCPC(ClassSix,"6");
            List<SubAccountCPC> ClassSeven = balanceDetailService.getClassSeven(dateString,"AL MORAFIQ");
            List<SubAccountCPC> FullClassSeven = accountDataManagerService.
                    processAccountDataCPC(ClassSeven,"7");

            Double res_net = esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NET DE L'EXERCICE");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String name = "";
            if (res_net < 0){
                name = "Perte nette";
            }else{
                name = "Bénéfice net";
            }

            Passage passage = new Passage(name,res_net,sdf.parse(dateString));

            // CONDITION IF WE HAVE OTHER VALUES FROM CPC ( PRODUIT ET CHARGE NON COURANT )
            List<Passage> list_pcNC = checkForPassageData(FullClassSix,FullClassSeven,sdf.parse(dateString));


            passages_list.add(passage);
            passages_list.addAll(list_pcNC);

            passages_final = passageService.processAccountData(passages_list);

            responseMap.put("total",res_net);
        }
        else {
            passages_final = passageService.processAccountData(passages_db);
        }

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

        // CLASS SIX
        List<SubAccountCPC> ClassSix = balanceDetailService.getClassSix(dateBilan,"AL MORAFIQ");
        List<SubAccountCPC> FullClassSix = accountDataManagerService.
                processAccountDataCPC(ClassSix,"6");

        // CLASS SEVEN
        List<SubAccountCPC> ClassSeven = balanceDetailService.getClassSeven(dateBilan,"AL MORAFIQ");
        List<SubAccountCPC> FullClassSeven = accountDataManagerService.
                processAccountDataCPC(ClassSeven,"7");

        List<List<Esg>> partsTFR = new ArrayList<>();

        partsTFR.add(esgService.processDataTFR(FullClassSix,FullClassSeven,1));
        partsTFR.add(esgService.processDataTFR(FullClassSix,FullClassSeven,2));
        partsTFR.add(esgService.processDataTFR(FullClassSix,FullClassSeven,3));
        partsTFR.add(esgService.processDataTFR(FullClassSix,FullClassSeven,4));
        partsTFR.add(esgService.processDataTFR(FullClassSix,FullClassSeven,5));

        List<List<Esg>> partsCAF = new ArrayList<>();

        partsCAF.add(esgService.processDataCAF(FullClassSix,FullClassSeven,1));
        partsCAF.add(esgService.processDataCAF(FullClassSix,FullClassSeven,2));

        responseMap.put("status", "success");

        responseMap.put("dataTFR", partsTFR);

        responseMap.put("total1",esgService.GetTotalDataEsg(partsTFR.get(0),2));
        responseMap.put("total2",esgService.GetTotalDataEsg(partsTFR.get(1),1));
        responseMap.put("total3",esgService.GetTotalDataEsg(partsTFR.get(2),1));
        responseMap.put("total4",esgService.GetTotalDataEsg(partsTFR.get(3),1));
        responseMap.put("total5",esgService.GetTotalDataEsg(partsTFR.get(4),1));

        responseMap.put("RE",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT D'EXPLOITATION"));
        responseMap.put("RF",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT FINANCIER"));
        responseMap.put("RC",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT COURANT"));
        responseMap.put("RNC",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NON COURANT"));
        responseMap.put("IR",esgService.GetResultat(FullClassSix,FullClassSeven,"Impôt sur les résultats"));
        responseMap.put("RN",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NET DE L'EXERCICE"));

        List<Esg> totalCAFList = new ArrayList<>();
        totalCAFList.addAll(partsCAF.get(0));
        totalCAFList.addAll(partsCAF.get(1));

        responseMap.put("dataCAF", partsCAF);
        responseMap.put("totalCAF",esgService.GetTotalDataEsg(totalCAFList,1));


        return ResponseEntity.ok().body(responseMap);
    }

    @RequestMapping(value="/passagesCPCFilter", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> passagesCPCFilter(@RequestParam("date") String dateBilan){
        Map<String, Object> responseMap = new HashMap<>();
        // CLASS SIX
        List<SubAccountCPC> ClassSix = balanceDetailService.getClassSix(dateBilan,"AL MORAFIQ");
        List<DetailCPC> FullClassSix = detailCPCService.processDataSix(ClassSix);

        // CLASS SEVEN
        List<SubAccountCPC> ClassSeven = balanceDetailService.getClassSeven(dateBilan,"AL MORAFIQ");
        List<DetailCPC> FullClassSeven = detailCPCService.processDataSeven(ClassSeven);

        List<List<DetailCPC>> partsCPCSix = new ArrayList<>();
        List<List<DetailCPC>> partsCPCSeven = new ArrayList<>();


        // CLASS SIX FILTER

        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.ACHAT_REVENDUS.getLabel()));
        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.ACHAT_CONSOMES.getLabel()));
        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.AUTRES_CHARGES_EXTERNES.getLabel()));
        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.CHARGES_PERSONEL.getLabel()));
        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.AUTRES_CHARGES_EXPLOITATION.getLabel()));
        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.AUTRES_CHARGES_FINANCIERES.getLabel()));
        partsCPCSix.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                DetailCPCCategoryClass6.AUTRES_CHARGES_NON_COURANTES.getLabel()));

        Double total1 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(0));
        Double total2 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(1));
        Double total3 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(2));
        Double total4 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(3));
        Double total5 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(4));
        Double total6 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(5));
        Double total7 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSix.get(6));

        // CLASS SEVEN FILTER

        partsCPCSeven.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                DetailCPCCategoryClass7.VENTES_MARCHANDISES.getLabel()));
        partsCPCSeven.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                DetailCPCCategoryClass7.VENTES_BIENS_SERVICES.getLabel()));
        partsCPCSeven.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                DetailCPCCategoryClass7.VAR_STOCK_PRODUITS.getLabel()));
        partsCPCSeven.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                DetailCPCCategoryClass7.AUTRES_PRODUITS_EXP.getLabel()));
        partsCPCSeven.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                DetailCPCCategoryClass7.REPRISE_EXPLOITATION.getLabel()));
        partsCPCSeven.add(detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                DetailCPCCategoryClass7.INTERETS_ASSIMILES.getLabel()));

        Double total8 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSeven.get(0));
        Double total9 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSeven.get(1));
        Double total10 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSeven.get(2));
        Double total11 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSeven.get(3));
        Double total12 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSeven.get(4));
        Double total13 = detailCPCService.GetTotalDataDetailCPCC(partsCPCSeven.get(5));

        responseMap.put("status", "success");
        responseMap.put("dataCPCSix", partsCPCSix);
        responseMap.put("dataCPCSeven", partsCPCSeven);


        responseMap.put("total1",total1);
        responseMap.put("total2",total2);
        responseMap.put("total3",total3);
        responseMap.put("total4",total4);
        responseMap.put("total5",total5);
        responseMap.put("total6",total6);
        responseMap.put("total7",total7);
        responseMap.put("total8",total8);
        responseMap.put("total9",total9);
        responseMap.put("total10",total10);
        responseMap.put("total11",total11);
        responseMap.put("total12",total12);
        responseMap.put("total13",total13);

        return ResponseEntity.ok().body(responseMap);
    }

    private List<Passage> checkForPassageData(List<SubAccountCPC> classSix, List<SubAccountCPC> classSeven, Date date) {
        List<Passage> passageList = new ArrayList<>();

        List<SubAccountCPC> dataset5 = accountDataManagerService.
                FilterAccountDataCPC(classSeven, AccountCategoryClass7.PRODUITS_NON_COURANTS.getLabel());
        List<SubAccountCPC> dataset6 = accountDataManagerService.
                FilterAccountDataCPC(classSix, AccountCategoryClass6.CHARGES_NON_COURANTES.getLabel());

        // CHECK CHARGES NC & PRODUITS NC
        Double chargeNC = dataset6.get(2).getBrut();
        Double produitNC = dataset5.get(3).getBrut();

        if(chargeNC != null){
            passageList.add(new Passage("AUTRES CHARGES NON COURANT",chargeNC,date));
        }
        if(produitNC != null){
            passageList.add(new Passage("AUTRES PRODUITS NON COURANTS",produitNC,date));
        }

        return passageList;
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