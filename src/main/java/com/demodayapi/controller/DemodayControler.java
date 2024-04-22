package com.demodayapi.controller;
import com.demodayapi.exceptions.ValidateBiggestBetweenInitEndException;

import com.demodayapi.models.Demoday;
import com.demodayapi.services.DemodayService;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




@RestController


@CrossOrigin
public class DemodayControler {
    

    @Autowired
    DemodayService DemodayService;

    @GetMapping("/demodays")
    public ResponseEntity<List<Demoday>> getDemodays() throws IOException, MethodArgumentNotValidException{
    
        List<Demoday> demodays = DemodayService.findAll();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }

    @PostMapping("/newDemoday")
    public ResponseEntity<Demoday> postDemoday(@RequestBody Demoday newDemoday) {
        try {
            
            if (DemodayService.ValidateBiggestInitEndDate(newDemoday)) throw new ValidateBiggestBetweenInitEndException();
                
                Demoday savedDemoday = DemodayService.saveDemoday(newDemoday);

                return new ResponseEntity<>(savedDemoday, HttpStatus.CREATED);
                
        } catch (ConstraintViolationException e) {
           
            return new ResponseEntity<>( HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }
}






