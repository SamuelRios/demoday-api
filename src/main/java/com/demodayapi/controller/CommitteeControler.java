package com.demodayapi.controller;
import java.util.List;
import com.demodayapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import com.demodayapi.exceptions.AreadyExistInProgressDemodayException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.models.Committee;
import com.demodayapi.models.Demoday;
import com.demodayapi.repositories.CommitteeRepository;
import com.demodayapi.services.CommitteeService;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.UserService;

import jakarta.validation.ConstraintViolationException;

@RestController
@CrossOrigin
public class CommitteeControler {

    @Autowired
    CommitteeService committeeService;

    @Autowired
    CommitteeRepository committeeRepository;

    @Autowired
    UserService userService;

      @Autowired
    DemodayService demodayService;

  

   


       
    
        @PostMapping("/newCommittee")
        public ResponseEntity<Committee> postDemoday(@RequestBody Committee newCommittee) {
            try {
                List<Demoday> demodayInProgress = demodayService.getDemodayInProgress();
    
                if (demodayInProgress == null || demodayInProgress.isEmpty()) {
                    throw new AreadyExistInProgressDemodayException();
                }
    
                Demoday demoday = demodayService.getDemodayWithBiggestValuePhase1();
                newCommittee.setDemoday(demoday);
    
                List<User> users = newCommittee.getUsers();
                if (users != null) {
                   newCommittee.setUsers(users);
                }
    
                Committee savedCommittee = committeeRepository.save(newCommittee);
    
                return new ResponseEntity<>(savedCommittee, HttpStatus.CREATED);
            } catch (ConstraintViolationException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("/deletecommittee/{id}")
        public ResponseEntity<Void> deleteCommittee(@PathVariable int id,HttpServletRequest request) {
        if(!userService.isLoggedUserAdmin(request))throw new UserIsNotAdminException();
        committeeService.deleteCommitteeById(id);
        return ResponseEntity.noContent().build();
    }

    }


  

