package com.api.demoimport.controller;

import com.api.demoimport.service.Implementation.PassageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api")
public class ViewController {
    @Autowired
    PassageServiceImpl passageService;

    /**
     * Controller class for handling view-related requests. Provides endpoints for returning different views.
     */


    @GetMapping("")
    public String index() {return "index";}

    @GetMapping("/passages")
    public String passages() {return "passages";}

    @GetMapping("/passagesImmo")
    public String passagesImmobilisations() {
        return "passagesImmobilisations";
    }

    @GetMapping("/esg")
    public String passagesESG(){ return "esg"; }

    @GetMapping("/detailCPC")
    public String detailCPC(){ return "detailCPC"; }

    @GetMapping("/immobilisation")
    public String immobilisation(){ return "Immobilisation"; }

    @GetMapping("/table12")
    public String tableau12(){ return "table12"; }

    @GetMapping("/table14")
    public String tableau14(){ return "table14"; }

    @GetMapping("/table15")
    public String tableau15(){ return "table15"; }

    @GetMapping("/table17")
    public String tableau17(){ return "table17"; }

    @GetMapping("/table18")
    public String tableau18(){ return "table18"; }

    @GetMapping("/table20")
    public String tableau20(){ return "table20"; }


}