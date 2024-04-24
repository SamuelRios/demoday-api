package com.demodayapi.controller;

import java.io.IOException;
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

import com.demodayapi.models.User;
import com.demodayapi.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPendingUsers() throws IOException, MethodArgumentNotValidException{
        List<User> users = userService.findAllPending();
        return new ResponseEntity(users, HttpStatus.OK);
    }
}
