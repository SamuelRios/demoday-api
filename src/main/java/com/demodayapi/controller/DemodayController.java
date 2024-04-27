package com.demodayapi.controller;
import com.demodayapi.enums.UserTypeEnum;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.ValidateBiggestBetweenInitEndException;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.User;
import com.demodayapi.services.AccCriteriaDemodayService;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.EvalCriteriaDemodayService;
import com.demodayapi.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
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
public class DemodayController {

    @Autowired
    DemodayService demodayService;
    @Autowired
    UserService userService;
    

    @GetMapping("/")
    public String  hello(){
        return "demoday-api is online";
    }

    @GetMapping("/demodays")
    public ResponseEntity<List<Demoday>> getDemodays() throws IOException, MethodArgumentNotValidException{
    
        List<Demoday> demodays = demodayService.findAll();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }

    @PostMapping("/newDemoday")
    public ResponseEntity<Demoday> postDemoday(@RequestBody Demoday newDemoday,HttpServletRequest request) {

        try {
            User userAdmin = userService.getLoggedUser(request);
                if ( userAdmin.getType().equals(UserTypeEnum.ADMIN) ){
            if (demodayService.ValidateBiggestInitEndDate(newDemoday)) throw new ValidateBiggestBetweenInitEndException();
            demodayService.setStatusProgress(newDemoday);
            Demoday savedDemoday = demodayService.saveDemoday(newDemoday); 
            
            return new ResponseEntity<>(savedDemoday, HttpStatus.CREATED);
             }

             throw new UserIsNotAdminException(); 

        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>( HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }


    
}






