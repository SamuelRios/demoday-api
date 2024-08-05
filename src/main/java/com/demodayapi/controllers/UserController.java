package com.demodayapi.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demodayapi.enums.UserTypeEnum;
import com.demodayapi.exceptions.UserCPFAlreadyExistsException;
import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.UserNotLoggedException;
import com.demodayapi.models.UpdateUserDTO;
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
    public ResponseEntity<List<User>> getPendingUsers(HttpServletRequest request) throws IOException, MethodArgumentNotValidException {
        if(this.userService.isLoggedUserAdmin(request)){
            List<User> users = userService.findAllPending();
            return new ResponseEntity<>(users, HttpStatus.OK);
       }
       throw new UserIsNotAdminException();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody User user)
            throws IOException, MethodArgumentNotValidException {
        try {
            if (userService.existsEmail(user.getEmail()))
                throw new UserEmailAlreadyExistsException();
            if (userService.existsCPF(user.getCpf()))
                throw new UserCPFAlreadyExistsException();
            userService.setCreatedUserStatus(user);
            String userId = this.firebaseService.createUser(user);
            user.setId(userId);
            User savedUser = userService.saveUser(user);
            if (savedUser != null) {
                Map<String, String> response = new HashMap<>();
                response.put("userId", userId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FirebaseAuthException e) {
            System.out.println("AQUI ===============================================================");
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUser( @PathVariable String id, @RequestBody @Valid UpdateUserDTO updateUserDTO, HttpServletRequest request ) throws IOException, MethodArgumentNotValidException {
        User loggedUser = this.userService.getLoggedUser(request);
        
        if(loggedUser.getId().equals(id) || this.userService.isLoggedUserAdmin(request)){
            User userEdit = this.userService.getUserById(id);
            if(updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().equals(userEdit.getEmail())){
                if (userService.existsEmail(updateUserDTO.getEmail()))
                    throw new UserEmailAlreadyExistsException();
                userEdit.setEmail(updateUserDTO.getEmail());
            }
            if (updateUserDTO.getName() != null) {
                userEdit.setName(updateUserDTO.getName());
            }
            
            if (updateUserDTO.getCpf() != null && !updateUserDTO.getCpf().equals(userEdit.getCpf())) {
                if (userService.existsCPF(updateUserDTO.getCpf()))
                    throw new UserCPFAlreadyExistsException();
                userEdit.setCpf(updateUserDTO.getCpf());
            }
            if (updateUserDTO.getUniversity() != null) {
                userEdit.setUniversity(updateUserDTO.getUniversity());
            }
            User savedUser = userService.saveUser(userEdit);
            if (savedUser != null) {
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            } 
        } 
        return new ResponseEntity<>("Usuário logado não tem permissão para alterar dados de outros usuários.", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getusers(HttpServletRequest request) {
        if(this.userService.isLoggedUserAdmin(request)){
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
       }
       throw new UserIsNotAdminException();
    }

    @GetMapping("/loggeduser")
    public User getLoggedUser(HttpServletRequest request) {
        User user = this.userService.getLoggedUser(request);
        if (user != null)
            return user;
        else
            throw new UserNotLoggedException();
    }

    @PostMapping("/setuserstatus")
    public User setUserStatus(HttpServletRequest request, @RequestParam(defaultValue = "userId") String userId, @RequestParam(defaultValue = "userStatus") String userStatus) {
       if(this.userService.isLoggedUserAdmin(request)){
            return this.userService.setUserStatus(userId, userStatus);
       }
       throw new UserIsNotAdminException();
    }


    @PostMapping("/finduserbytype/{type}")
     public ResponseEntity <List <User>> findUserBytype(@PathVariable UserTypeEnum type, HttpServletRequest request) {
        System.out.println("AQUI*********************************************");
        if (!userService.isLoggedUserAdmin(request)) throw new UserIsNotAdminException();
        List <User> usersByType =userService.listOfTypeUser(type);
        System.out.println(usersByType);
        return new ResponseEntity<>(usersByType, HttpStatus.OK);
}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id, HttpServletRequest request) {
        if(this.userService.isLoggedUserAdmin(request)){
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        throw new UserIsNotAdminException();
    }
}
