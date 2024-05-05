package com.api.demoimport.controller;


import com.api.demoimport.message.ResponseMessage;
import com.api.demoimport.service.Implementation.PassageServiceImpl;
import com.api.demoimport.service.Implementation.ReportServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/report")
public class BilanController {

    @Autowired
    ReportServiceImpl reportServiceImpl;
    @Autowired
    PassageServiceImpl passageService;

    /**
     * Controller class for handling requests related to financial reports.
     * Provides endpoints for generating bilan/cpc PDF format
     */

    @GetMapping("/bilan-passif")
    public ResponseEntity  getBilanPassif(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException {
        try{
            ByteArrayOutputStream reportstream = reportServiceImpl.exportReportPassif(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);

        }catch(RuntimeException e){
            e.printStackTrace();
            String message = "Failed to upload bilan passif " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/bilan-actif")
    public ResponseEntity  getBilanActif(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException {
        try{
            ByteArrayOutputStream reportstream = reportServiceImpl.exportReportActif(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);

        }catch(RuntimeException e){

            String message = "Failed to upload bilan actif " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/cpc")
    public ResponseEntity  getCPC(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException {
        try{
            ByteArrayOutputStream reportstream = reportServiceImpl.exportCPC(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);

        }catch(RuntimeException e){
            String message = "Failed to upload CPC " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
