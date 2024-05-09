package com.demodayapi.controllers;
import com.demodayapi.exceptions.AreadyExistInProgressDemodayException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.ValidateBiggestBetweenInitEndException;
import com.demodayapi.models.Demoday;
import com.demodayapi.services.DemodayService;
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
    public ResponseEntity<Demoday> postDemoday(@RequestBody Demoday newDemoday, HttpServletRequest request) {
        try {
            if(this.userService.isLoggedUserAdmin(request)){
                if(!this.demodayService.hasDemodayInProgress()){

                    if (demodayService.ValidateBiggestInitEndDate(newDemoday)) throw new ValidateBiggestBetweenInitEndException();
                    Demoday savedDemoday = demodayService.saveDemoday(newDemoday); 
                    return new ResponseEntity<>(savedDemoday, HttpStatus.CREATED);

                } throw new AreadyExistInProgressDemodayException();
            } throw new UserIsNotAdminException();
        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getactivedemoday")
    public ResponseEntity<List<Demoday>> getActiveDemoday() throws IOException, MethodArgumentNotValidException{
    
        List<Demoday> demodays = demodayService.getInProgressDemodays();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }
    
}






