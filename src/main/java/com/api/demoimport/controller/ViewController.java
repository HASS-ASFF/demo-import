package com.api.demoimport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api")
public class ViewController {

    @GetMapping("/passages")
    public String passagesCRUD() {
        return "passages";
    }

}