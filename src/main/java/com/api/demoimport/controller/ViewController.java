package com.api.demoimport.controller;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.enums.PassageCategory;
import com.api.demoimport.service.Implementation.PassageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api")
public class ViewController {
    @Autowired
    PassageServiceImpl passageService;

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





}