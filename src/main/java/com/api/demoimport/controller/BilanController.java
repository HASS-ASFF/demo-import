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
     * Provides endpoints for generating bilan PDF format
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

    @GetMapping("/passage")
    public ResponseEntity getPassage(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            return null;
        }catch(RuntimeException e){
            String message = "Failed to upload passage " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/esg")
    public ResponseEntity getEsg(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            return null;
        }catch(RuntimeException e){
            String message = "Failed to upload ESG " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/detailCPC")
    public ResponseEntity getDetailCPC(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            return null;
        }catch(RuntimeException e){
            String message = "Failed to upload detail CPC " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/table12")
    public ResponseEntity getTable12(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            ByteArrayOutputStream reportstream = reportServiceImpl.exportDetailTva(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);
        }catch(RuntimeException e){
            String message = "Failed to upload table " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/table14")
    public ResponseEntity getTable14(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            ByteArrayOutputStream reportstream = reportServiceImpl.exportTable14(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);
        }catch(RuntimeException e){
            String message = "Failed to upload table " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/table15")
    public ResponseEntity getTable15(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            ByteArrayOutputStream reportstream = reportServiceImpl.exportTable15(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);
        }catch(RuntimeException e){
            String message = "Failed to upload table " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/table17")
    public ResponseEntity getTable17(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            ByteArrayOutputStream reportstream = reportServiceImpl.exportTable17(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);
        }catch(RuntimeException e){
            String message = "Failed to upload table " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/table18")
    public ResponseEntity getTable18(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            ByteArrayOutputStream reportstream = reportServiceImpl.exportTable18(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);
        }catch(RuntimeException e){
            String message = "Failed to upload table " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/table20")
    public ResponseEntity getTable20(@RequestParam("dateBilan") String date, @RequestParam("companyName") String company_name) throws JRException{
        try {
            ByteArrayOutputStream reportstream = reportServiceImpl.exportTable20(date,company_name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity(reportstream.toByteArray(),httpHeaders,HttpStatus.OK);
        }catch(RuntimeException e){
            String message = "Failed to upload table " + e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

}
