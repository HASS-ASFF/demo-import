package com.api.demoimport.controller;


import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.BilanActif.BilanActif;
import com.api.demoimport.entity.BilanActif.FormatUtils;
import com.api.demoimport.entity.BilanActif.MainAccount;
import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.service.AccountDataManager;
import com.api.demoimport.service.ExcelHelperServiceImpl;
import com.api.demoimport.message.ResponseMessage;
import com.api.demoimport.service.ExcelBalanceDetailServiceImpl;
import com.api.demoimport.service.ExcelPlanComptableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    ExcelPlanComptableServiceImpl fileService;

    @Autowired
    ExcelBalanceDetailServiceImpl fileServiceBalance;

    @Autowired
    AccountDataManager accountDataManager;

    // Méthode POST pour télécharger un fichier plan comptable
    @PostMapping("/upload-plan-comptable")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        // Vérifie si le fichier est au format Excel
        if (ExcelHelperServiceImpl.hasExcelFormat(file)) {
            try {

                // Sauvegarde le fichier plan comptable
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                //e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    // Méthode GET pour récupérer la liste des plans comptables
    @GetMapping("/plan-comptable")
    public ResponseEntity<List<PlanComptable>> getPlanComptable() {
        try {
            List<PlanComptable> planComptables = fileService.getPlanComptables();

            if (planComptables.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(planComptables, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Méthode pour télécharger un fichier détail de balance
    @PostMapping("/upload-balance-detail")
    public ResponseEntity<ResponseMessage> uploadFileBalance(@RequestParam("file") MultipartFile file) {
        String message = "";

        // Vérifie si le fichier est au format Excel
        if (ExcelHelperServiceImpl.hasExcelFormat(file)) {
            try {

                // Sauvegarde le fichier détail de balance
                fileServiceBalance.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                System.out.println(e.getMessage());
                //e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    // Méthode pour récupérer la liste des détails de balance
    @GetMapping("/balancedetails")
    public ResponseEntity<List<BalanceDetail>> getBalanceDetail() {
        try {
            List<BalanceDetail> balanceDetails = fileServiceBalance.getBalanceDetails();

            if (balanceDetails.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(balanceDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bilan-actif")
    public String getBilanActif(Model model) {

        // class two
        List<BilanActif> results_class2 = fileServiceBalance.getClassTwo() ;
        List<MainAccount> accountDataMap2 = accountDataManager.processAccountData(results_class2,2);
        List<Double> total_account2 = accountDataManager.GetTotalAccount(accountDataMap2);
        // class three
        List<BilanActif> results_class3 = fileServiceBalance.getClassThree();
        List<MainAccount> accountDataMap3 = accountDataManager.processAccountData(results_class3,3);
        List<Double> total_account3 = accountDataManager.GetTotalAccount(accountDataMap3);
        // class five
        List<BilanActif> results_class5 = fileServiceBalance.getClassFive();
        List<MainAccount> accountDataMap5 = accountDataManager.processAccountData(results_class5,5);
        List<Double> total_account5 = accountDataManager.GetTotalAccount(accountDataMap5);

        model.addAttribute("results_c2", accountDataMap2);
        model.addAttribute("results_c3", accountDataMap3);
        model.addAttribute("results_c5",accountDataMap5);

        model.addAttribute("total_c2",total_account2);
        model.addAttribute("total_c3",total_account3);
        model.addAttribute("total_c5",total_account5);

        List<Double> totalGeneraux = new ArrayList<>();

        totalGeneraux.add(
                FormatUtils.formatDecimal(total_account2.get(0)+total_account3.get(0)+total_account5.get(0)));
        totalGeneraux.add(
                FormatUtils.formatDecimal(total_account2.get(1)+total_account3.get(1)+total_account5.get(1)));
        totalGeneraux.add(
                FormatUtils.formatDecimal(total_account2.get(2)+total_account3.get(2)+total_account5.get(2)));

        model.addAttribute("Total_gen",totalGeneraux);
        return "bilanactif";
    }
}
