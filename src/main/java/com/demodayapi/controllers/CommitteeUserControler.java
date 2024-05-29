package com.demodayapi.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.demodayapi.exceptions.TherIsNotActiveDemodayException;
import com.demodayapi.exceptions.UserCPFAlreadyExistsException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.models.CommitteeUser;
import com.demodayapi.models.Demoday;
import com.demodayapi.services.CommitteeService;
import com.demodayapi.services.CommitteeUserService;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestController
@CrossOrigin
public class CommitteeUserControler {

    @Autowired
    UserService userService;
    @Autowired
    CommitteeUserService committeeUserService;
    @Autowired
    CommitteeService committeeService;
    @Autowired
    DemodayService demodayService;

    @PostMapping("/adduserscommittee")
    public ResponseEntity<CommitteeUser> postProject(@RequestBody CommitteeUser newUsersCommittee,
            HttpServletRequest request) {
        try {

            CommitteeUser CommitteeUser = new CommitteeUser();
            List<Demoday> demodayInProgress= demodayService.getDemodayInProgress();
            if (demodayInProgress==null) throw new TherIsNotActiveDemodayException();
            Demoday demoday = demodayService.getDemodayInProgress().get(0);
            // checa se existe demoday
            System.out.println(demoday.getId());

            List<CommitteeUser> listUserCommittee = committeeUserService.allUsersActiveCommittee(demoday.getId());
            if (!listUserCommittee.isEmpty()) {
                System.out.println("Iniciando loop");
                for (CommitteeUser userCommittee : listUserCommittee) {
                    String userCommiteeInDb = userCommittee.getUser().getId();
                    String userTrySaving = newUsersCommittee.getUser().getId();

                    if (userTrySaving.equals(userCommiteeInDb))
                        throw new UserCPFAlreadyExistsException();

                }

                CommitteeUser.setUser(newUsersCommittee.getUser());
                demoday = demodayService.getDemodayInProgress().get(0);
                CommitteeUser.setCommittee(demoday.getCommittee());
                committeeUserService.saveUserCommittee(CommitteeUser);

            } else {
                CommitteeUser.setUser(newUsersCommittee.getUser());
                demoday = demodayService.getDemodayInProgress().get(0);
                CommitteeUser.setCommittee(demoday.getCommittee());
                committeeUserService.saveUserCommittee(CommitteeUser);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }

    }

    @GetMapping("/getcommitteeusers")
    public ResponseEntity<List<CommitteeUser>> getActiveUsersCommittee() throws IOException, MethodArgumentNotValidException {
        Demoday demoday = demodayService.getDemodayInProgress().get(0);
        if (demoday == null)
            throw new TherIsNotActiveDemodayException();
        List<CommitteeUser> listUserCommittee = committeeUserService.allUsersActiveCommittee(demoday.getId());
        return new ResponseEntity<>(listUserCommittee, HttpStatus.OK);
    }

    @GetMapping("/getcommitteeusersbyid/{id}")
    public ResponseEntity<List<CommitteeUser>> getActiveUsersById(@PathVariable int id) throws IOException, MethodArgumentNotValidException {
         
        List<CommitteeUser> listUserCommittee = committeeUserService.allUsersActiveCommittee(id);
        return new ResponseEntity<>(listUserCommittee, HttpStatus.OK);
    }


    @DeleteMapping("/deletealluserscommittee")
    public ResponseEntity<Void> deleteCommittee(HttpServletRequest request) {
        if (!userService.isLoggedUserAdmin(request))
            throw new UserIsNotAdminException();

        List <Demoday> demoday = demodayService.getDemodayInProgress();
        if (demoday == null)throw new TherIsNotActiveDemodayException();
        committeeUserService.deleteAllCommitteeUsers(demoday.get(0).getId());
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/deleteusercommittee/{id}")
    public ResponseEntity<Void> deleteCommitteeById(@PathVariable String id, HttpServletRequest request) {
        if (!userService.isLoggedUserAdmin(request))
            throw new UserIsNotAdminException();
        List <Demoday> demoday = demodayService.getDemodayInProgress();
        if (demoday == null)throw new TherIsNotActiveDemodayException();
        committeeUserService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
