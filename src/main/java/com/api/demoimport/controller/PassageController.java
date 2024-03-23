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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PassageController {

    @Autowired
    PassageServiceImpl passageService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Object> addPassage(@RequestBody Passage passage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("data", passageService.createPassage(passage));
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPassagesByDate", method = RequestMethod.GET)
    public ResponseEntity<Object> getPassagesByDate(@RequestParam("date") String dateString) {
        Map<String, Object> responseMap = new HashMap<>();
        List<Passage> passages = passageService.findPassages(dateString);
        responseMap.put("status", "success");
        responseMap.put("data",passageService.processAccountData(passages));
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/savePassage")
    public ResponseEntity<Object> savePassage(@RequestBody Passage passage) {
        Map<String, Object> responseMap = new HashMap<>();

        // Vérifier si le passage existe déjà en fonction de certains critères, par exemple, le nom et la date
        Passage existingPassage = passageService.findByNameAndDate(passage.getName(), passage.getDate());

        if (existingPassage != null) {
            // Mettre à jour les valeurs existantes du passage avec les nouvelles valeurs
            existingPassage.setAmountPlus(passage.getAmountPlus());
            existingPassage.setAmountMinus(passage.getAmountMinus());

            // Enregistrer le passage mis à jour dans la base de données
            Passage updatedPassage = passageService.updatePassage(existingPassage);

            if (updatedPassage != null) {
                responseMap.put("status", "success");
                responseMap.put("data", updatedPassage);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "Échec de la mise à jour du passage.");
                return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Si le passage n'existe pas encore, l'enregistrer comme nouveau passage
            Passage savedPassage = passageService.createPassage(passage);

            if (savedPassage != null) {
                responseMap.put("status", "success");
                responseMap.put("data", savedPassage);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "Échec de l'enregistrement du passage.");
                return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }




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
