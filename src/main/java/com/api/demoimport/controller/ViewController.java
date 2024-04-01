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
    public String passages(Model model) {
        List<Passage> passages_db = passageService.getAllPassages();
        List<Passage> passages_final = passageService.processAccountData(passages_db);
        //System.out.println(passages_final);
        model.addAttribute("part1", passageService.FilterPassages(passages_final, PassageCategory.RESULTAT_NET_COMPTABLE.getMain_name()));
        model.addAttribute("part2", passageService.FilterPassages(passages_final,PassageCategory.REINTEGRATIONS_FISCALES.getMain_name()));
        model.addAttribute("part3", passageService.FilterPassages(passages_final,PassageCategory.DEDUCTIONS_FISCALES.getMain_name()));
        model.addAttribute("part4", passageService.FilterPassages(passages_final,PassageCategory.RESULTAT_BRUT_FISCAL.getMain_name()));
        model.addAttribute("part5", passageService.FilterPassages(passages_final,PassageCategory.REPORTS_DEFICITAIRES_IMPUTES.getMain_name()));
        model.addAttribute("part6", passageService.FilterPassages(passages_final,PassageCategory.RESULTAT_NET_FISCAL.getMain_name()));
        model.addAttribute("part7", passageService.FilterPassages(passages_final,PassageCategory.CUMUL_DES_AMORTISSEMENTS_FISCALEMENT_DIFFERES.getMain_name()));
        model.addAttribute("part8", passageService.FilterPassages(passages_final,PassageCategory.CUMUL_DES_DEFICITS_FISCAUX_RESTANT_A_REPORTER.getMain_name()));
        return "passages";
    }

    @GetMapping("/passagesImmo")
    public String passagesImmobilisations(Model model) {
        return "passagesImmobilisations";
    }


}