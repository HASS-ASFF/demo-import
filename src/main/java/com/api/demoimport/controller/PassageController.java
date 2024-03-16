package com.api.demoimport.controller;

import com.api.demoimport.dto.ServiceResponse;
import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.service.Implementation.PassageServiceImpl;
import com.api.demoimport.service.PassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PassageController {

    @Autowired
    PassageServiceImpl passageService;

    /*@GetMapping("/passages/{datebilan}")
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
    }*/

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Object> addPassage(@RequestBody Passage passage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("data", passageService.createPassage(passage));
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPassages", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPassages() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("data", passageService.getAllPassages());
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
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
}
