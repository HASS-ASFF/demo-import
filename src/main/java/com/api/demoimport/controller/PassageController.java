package com.api.demoimport.controller;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.service.Implementation.PassageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PassageController {

    @Autowired
    PassageServiceImpl passageService;

    @GetMapping("/passages/{datebilan}")
    public ResponseEntity<List<Passage>> getPassagesByBilanDate(@PathVariable(value = "dateBilan") String bilanDate) {
        List<Passage> passages = passageService.findPassages(bilanDate);
        if (passages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(passages);
    }

    @PostMapping("/passage")
    public Passage createPassage(@RequestBody Passage passage) {
        return passageService.createPassage(passage);
    }
}
