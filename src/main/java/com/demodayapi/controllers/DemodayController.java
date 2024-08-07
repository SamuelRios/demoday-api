package com.demodayapi.controllers;
import com.demodayapi.exceptions.AccEvalCriteriaNameCanNotBeNullException;
import com.demodayapi.exceptions.AreadyExistInProgressDemodayException;
import com.demodayapi.exceptions.TherIsNotActiveDemodayException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.ValidateBiggestBetweenInitEndException;
import com.demodayapi.models.Committee;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.Finalist;
import com.demodayapi.services.CommitteeService;
import com.demodayapi.services.CommitteeUserService;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.FinalistService;
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

     @Autowired
    CommitteeService committeeService;

    @Autowired
    CommitteeUserService committeeUserService;

    @Autowired
    FinalistService finalistService;

    @GetMapping("/")
    public String hello() {
        return "demoday-api is online";
    }
    
    @GetMapping("/getalldemodays")
    public ResponseEntity<List<Demoday>> getAllDemodays() throws IOException, MethodArgumentNotValidException {
        List<Demoday> demodays = demodayService.getAllDemodays();
        return new ResponseEntity<>(demodays, HttpStatus.OK);
    }


    @PostMapping("/newdemoday")
    public ResponseEntity<Demoday> postDemoday(@RequestBody Demoday newDemoday, HttpServletRequest request) {
        try {
            System.out.println("ola ola ola hiiiiiiiiiiii : : :: ) ) ) ) aqui 1");
            List<Demoday> demodayInProgress= demodayService.getDemodayInProgress();

            if (this.userService.isLoggedUserAdmin(request)) {
                System.out.println("ola ola ola hiiiiiiiiiiii : : :: ) ) ) ) aqui 2");
                if ((demodayInProgress == null)) {
                    System.out.println("ola ola ola hiiiiiiiiiiii : : :: ) ) ) ) aqui 3");
                    if (demodayService.ValidateBiggestInitEndDate(newDemoday))
                        throw new ValidateBiggestBetweenInitEndException();
                        System.out.println("ola ola ola hiiiiiiiiiiii : : :: ) ) ) ) aqui 4");
                        if(this.demodayService.verifyAccEvalCriteriaAndNameExists(newDemoday)) throw new AccEvalCriteriaNameCanNotBeNullException();
                    Demoday savedDemoday = demodayService.saveDemoday(newDemoday);
                    demodayInProgress = demodayService.getDemodayInProgress();
                    Committee committee = new Committee();
                    committee.setDemoday(demodayInProgress.get(0));
                    committeeService.saveCommittee(committee);
                    System.out.println("ola ola ola hiiiiiiiiiiii : : :: ) ) ) ) aqui 5");
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
    public ResponseEntity<Void> deleteDemoday(@PathVariable int id, HttpServletRequest request) {

    if(!userService.isLoggedUserAdmin(request))throw new UserIsNotAdminException();
    committeeUserService.deleteAllCommitteeUsers(id); 
    demodayService.deleteDemodayById(id); 
    return ResponseEntity.noContent().build();
}

    @GetMapping("/getdemodayinfo/{demoday_id}")
    public ResponseEntity<Demoday> getDemodayById(@PathVariable int demoday_id) {
        Demoday demoday = demodayService.findById(demoday_id);
        if (demoday != null) {
            return new ResponseEntity<>(demoday, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/setfinalists")
    public ResponseEntity<?> createFinalists(@RequestBody List<Finalist> finalists, HttpServletRequest request) {
        if (this.userService.isLoggedUserAdmin(request) || this.userService.isLoggedUserProfessor(request)) {
            for(Finalist finalist: finalists){
                try {
                    Finalist savedFinalist = this.finalistService.save(finalist);
                    System.out.println(savedFinalist.getId());
                } catch (Exception e){
                    if (e.getMessage().contains("Duplicate entry")) {
                        System.out.println("Finalista já cadastrado.");
                    } else {
                        e.printStackTrace();
                        return new ResponseEntity<>("Erro Interno.", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            return new ResponseEntity<>("Finalistas registrados.", HttpStatus.OK);
        }
        throw new UserIsNotAdminException("Usuário Admin ou Professor requerido");
    }

    @PostMapping("/deletefinalists")
    public ResponseEntity<?> deleteFinalistsByProjectIds(@RequestBody List<Integer> projectIds, HttpServletRequest request) {
        if (this.userService.isLoggedUserAdmin(request) || this.userService.isLoggedUserProfessor(request)) {
            for(Integer projectId: projectIds){
                try {
                    System.out.println("aqui 1?");
                    this.finalistService.deleteByProjectId(projectId);
                } catch (Exception e){
                    e.printStackTrace();
                    return new ResponseEntity<>("Erro Interno.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>("Projeto(s) removido(s) da lista de Finalistas.", HttpStatus.OK);
        }
        throw new UserIsNotAdminException("Usuário Admin ou Professor requerido");
    }


    // @PostMapping("/getprojectsdemodayscore/{id}")
    // public String getProjectsDemodayScore(@PathVariable("id") int demodayId, HttpServletRequest request) {
    //     DemodayStatusEnum demodayStatus  = demodayService.getDemodayStatus();
    //     if(demodayStatus)
    //     if(demodayStatus = null || demodayStatus.equals(DemodayStatusEnum.PHASE1) || demodayStatus.equals(DemodayStatusEnum.PHASE2)){
    //         throw new PhaseThreeNotCompletedException();
    //     }
    //     System.out.println(demodayId);
    //     return null;
    // }
    

}
