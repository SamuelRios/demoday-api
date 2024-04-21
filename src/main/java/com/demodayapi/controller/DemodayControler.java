package com.demodayapi.controller;
import com.demodayapi.models.Demoday;
import com.demodayapi.services.DemodayService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController


@CrossOrigin
public class DemodayControler {
    

    @Autowired
    DemodayService NewDemodayService;

    @GetMapping("/demodays")
    public ResponseEntity<List<Demoday>> getDemodays() throws IOException, MethodArgumentNotValidException{
    
        List<Demoday> demodays = NewDemodayService.findAll();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }

    @PostMapping("/newDemoday")
    public ResponseEntity<Demoday> postDemoday(@RequestBody Demoday newDemoday) {
    Demoday savedDemoday = NewDemodayService.saveDemoday(newDemoday);
    return new ResponseEntity<>(savedDemoday, HttpStatus.CREATED);
}
   
}





