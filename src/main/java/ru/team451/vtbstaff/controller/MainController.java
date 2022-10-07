package ru.team451.vtbstaff.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainController {

    @GetMapping("/test")
    public String getTestString(){
        return "Zdorov!";
    }
}
