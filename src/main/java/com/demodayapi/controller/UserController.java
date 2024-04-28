package com.demodayapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demodayapi.exceptions.UserCPFAlreadyExistsException;
import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.exceptions.UserNotLoggedException;
import com.demodayapi.models.User;
import com.demodayapi.services.FirebaseService;
import com.demodayapi.services.UserService;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    FirebaseService firebaseService;

    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPendingUsers() throws IOException, MethodArgumentNotValidException{
        List<User> users = userService.findAllPending();
        return new ResponseEntity(users, HttpStatus.OK);
    }

     @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createUser(@Valid @RequestBody User user) throws IOException, MethodArgumentNotValidException {
        try {
            if(userService.existsEmail(user.getCpf())) throw new UserEmailAlreadyExistsException();
            if(userService.existsCPF(user.getCpf())) throw new UserCPFAlreadyExistsException();
            userService.setUserStatus(user);
            String userId = this.firebaseService.createUser(user);
            user.setId(userId);
            User savedUser = userService.saveUser(user);
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
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/loggeduser")
    public User getLoggedUser(HttpServletRequest request) {
        User user = this.userService.getLoggedUser(request);
        if(user != null) return user;
        else throw new UserNotLoggedException();
    }
    
}
