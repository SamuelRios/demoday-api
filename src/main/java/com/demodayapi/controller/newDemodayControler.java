package com.demodayapi.controller;
import com.demodayapi.models.newDemoday;
import com.demodayapi.services.NewDemodayService;

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
public class newDemodayControler {
    

    @Autowired
    NewDemodayService NewDemodayService;

    @GetMapping("/demodays")
    public ResponseEntity<List<newDemoday>> getDemodays() throws IOException, MethodArgumentNotValidException{
    
        List<newDemoday> demodays = NewDemodayService.findAll();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }

    @PostMapping("/newDemoday")
    public ResponseEntity<newDemoday> postDemoday(@RequestBody newDemoday newDemoday) {
        System.out.println(newDemoday.getName());
        System.out.println(newDemoday.getPhaseOneInit());
        System.out.println(newDemoday.getPhaseOneEnd());
        System.out.println(newDemoday.getYear());
        System.out.println("xxxXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    newDemoday savedDemoday = NewDemodayService.saveDemoday(newDemoday);
    
    return new ResponseEntity<>(savedDemoday, HttpStatus.CREATED);
}
   
}





