package com.demodayapi.controllers;
import com.demodayapi.exceptions.AccEvalCriteriaNameCanNotBeNullException;
import com.demodayapi.exceptions.AreadyExistInProgressDemodayException;
import com.demodayapi.exceptions.TherIsNotActiveDemodayException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String hello() {
        return "demoday-api is online";
    }

    @GetMapping("/getdemodayinfo")
    public ResponseEntity<List<Demoday>> getDemodays() throws IOException, MethodArgumentNotValidException {
        List<Demoday> demodays = demodayService.findAll();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }

    @PostMapping("/newDemoday")
    public ResponseEntity<Demoday> postDemoday(@RequestBody Demoday newDemoday, HttpServletRequest request) {
        try {

            List<Demoday> demodayInProgress= demodayService.getDemodayInProgress();

            if (this.userService.isLoggedUserAdmin(request)) {
                if ((demodayInProgress == null)) {
                    if (demodayService.ValidateBiggestInitEndDate(newDemoday))
                        throw new ValidateBiggestBetweenInitEndException();
                        if(this.demodayService.verifyAccEvalCriteriaAndNameExists(newDemoday)) throw new AccEvalCriteriaNameCanNotBeNullException();
                    Demoday savedDemoday = demodayService.saveDemoday(newDemoday);
                    return new ResponseEntity<>(savedDemoday, HttpStatus.CREATED);
                }
                throw new AreadyExistInProgressDemodayException();
            }
            throw new UserIsNotAdminException();
        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getactivedemoday")
    public ResponseEntity<List<Demoday>> getActiveDemoday() throws IOException, MethodArgumentNotValidException {
        List<Demoday> demoday = demodayService.getDemodayInProgress();
        if (demoday == null)
            throw new TherIsNotActiveDemodayException();
        return new ResponseEntity<>(demoday, HttpStatus.OK);
    }


     @DeleteMapping("/deletedemoday/{id}")
        public ResponseEntity<Void> deleteDemoday(@PathVariable int id,HttpServletRequest request) {
        if(!userService.isLoggedUserAdmin(request))throw new UserIsNotAdminException();
        demodayService.deleteDemodayById(id); 
        return ResponseEntity.noContent().build();
    }

}
