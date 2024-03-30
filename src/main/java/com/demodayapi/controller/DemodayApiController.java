package com.demodayapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@CrossOrigin
public class DemodayApiController {


    @GetMapping("/")
    public String  hello(){
        return "demoday-api is online";
    }

    
}
