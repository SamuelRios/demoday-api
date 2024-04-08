package com.demodayapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demodayapi.exceptions.UserCPFAlreadyExistsException;
import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.models.User;
import com.demodayapi.services.FirebaseService;
import com.demodayapi.services.UserService;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController

@CrossOrigin
public class DemodayApiController {

    @GetMapping("/")
    public String  hello(){
        return "demoday-api is online";
    }

    @PostMapping("/createuser")
    public ResponseEntity<Map<String,String>> postMethodName(@Valid @RequestBody User user) throws IOException, MethodArgumentNotValidException {
        try {
            if(UserService.existsEmail(user.getCpf())) throw new UserEmailAlreadyExistsException();
            if(UserService.existsCPF(user.getCpf())) throw new UserCPFAlreadyExistsException();
            String userId = FirebaseService.createUser(user);
            user.setId(userId);
            User savedUser = UserService.saveUser(user);
            if(savedUser != null){
                Map<String, String> response = new HashMap<>();
                response.put("userId", userId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 
        catch (FirebaseAuthException e) {
            System.out.println("AQUI ===============================================================");
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getusers() {
        return new ResponseEntity<>(UserService.findAll(), HttpStatus.OK);
    }
    
    
    
}
