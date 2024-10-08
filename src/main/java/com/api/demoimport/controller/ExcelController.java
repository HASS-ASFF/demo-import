package com.api.demoimport.controller;


import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.service.Implementation.ExcelHelperServiceImpl;
import com.api.demoimport.message.ResponseMessage;
import com.api.demoimport.service.Implementation.BalanceDetailServiceImpl;
import com.api.demoimport.service.Implementation.ImmobilisationServiceImpl;
import com.api.demoimport.service.Implementation.PlanComptableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    PlanComptableServiceImpl fileService;

    @Autowired
    BalanceDetailServiceImpl fileServiceBalance;

    @Autowired
    ImmobilisationServiceImpl fileServiceImmo;

    /**
     * Controller class for handling requests related to Excel files.
     * Provides endpoints for uploading plan comptable, balance detail, and immobilisation Excel files.
     */



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

                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    // Méthode pour télécharger un fichier détail de balance
    @PostMapping("/upload-balance-detail")
    public ResponseEntity<ResponseMessage> uploadFileBalance(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("dateBalance") String date,
                                                             @RequestParam("companyName") String company_name)
    {
        String message = "";

        // Vérifie si le fichier est au format Excel
        if (ExcelHelperServiceImpl.hasExcelFormat(file)) {
            try {

                // Sauvegarde le fichier détail de balance
                fileServiceBalance.save(file,date,company_name);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }



    // Méthode pour télécharger un fichier immobilisation
    @PostMapping("/upload-immobilisation")
    public ResponseEntity<ResponseMessage> uploadFileImmobilisation(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("dateBalance") String date,
                                                             @RequestParam("companyName") String company_name)
    {
        String message = "";

        // Vérifie si le fichier est au format Excel
        if (ExcelHelperServiceImpl.hasExcelFormat(file)) {
            try {

                // Sauvegarde le fichier immobilisation
                fileServiceImmo.save(file,date,company_name);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


}



// FOR TESTING ENDPOINTS FOR CPC CLASSES
   /* @GetMapping("/class6")
    public ResponseEntity<List<SubAccountCPC>> getClassSixDetails(@RequestParam("dateBalance") String date,
                                                                  @RequestParam("companyName") String company_name) throws Exception {
        try {
            List<SubAccountCPC> subAccountCPCS = fileServiceBalance.getClassSix(date,company_name);
            List<SubAccountCPC> FilteredCPCS = accountDataManagerServiceImpl.
                    processAccountDataCPC(subAccountCPCS,"6");

            //FilteredCPCS.forEach(x -> System.out.println(x.getLibelle()));

            if (FilteredCPCS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }


            return new ResponseEntity<>(FilteredCPCS, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/class7")
    public ResponseEntity<List<SubAccountCPC>> getClassSevenDetails(@RequestParam("dateBalance") String date,
                                                                    @RequestParam("companyName") String company_name){
        try {
            List<SubAccountCPC> subAccountCPCS = fileServiceBalance.getClassSeven(date,company_name);

            List<SubAccountCPC> FilteredCPCS = accountDataManagerServiceImpl.
                    processAccountDataCPC(subAccountCPCS,"7");

            if (FilteredCPCS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(FilteredCPCS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/