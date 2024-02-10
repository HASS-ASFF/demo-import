package com.api.demoimport.controller;


import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.BilanActif.BilanActif;
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
        List<BilanActif> results_class2 = fileServiceBalance.getClassTwo() ;
        List<MainAccount> accountDataMap = accountDataManager.processAccountData(results_class2);

        List<Object[]> results_class3 = fileServiceBalance.getClassThree();
        List<Object[]> results_class5 = fileServiceBalance.getClassFive();

        model.addAttribute("results_c2", accountDataMap);

        model.addAttribute("results_c3", results_class3);
        model.addAttribute("results_c5",results_class5);
        return "bilanactif";
    }
}
