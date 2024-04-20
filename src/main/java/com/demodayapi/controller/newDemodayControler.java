package com.demodayapi.controller;
import com.demodayapi.models.newDemoday;
import com.demodayapi.services.NewDemodayService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/demoday")

@CrossOrigin
public class newDemodayControler {
    

    @Autowired
    NewDemodayService NewDemodayService;

    @GetMapping("/newDemoday")
    public ResponseEntity<List<newDemoday>> getDemodays() throws IOException, MethodArgumentNotValidException{
        List<newDemoday> demodays = NewDemodayService.findAll();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }
}





