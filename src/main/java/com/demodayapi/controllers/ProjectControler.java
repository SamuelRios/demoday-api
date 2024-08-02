package com.demodayapi.controllers;

import com.demodayapi.enums.DemodayStatusEnum;
import com.demodayapi.enums.ProjectStatusEnum;
import com.demodayapi.enums.ProjectTypeEnum;
import com.demodayapi.enums.UserTypeEnum;
import com.demodayapi.exceptions.DuplicateEvaluationByCriteriaException;
import com.demodayapi.exceptions.ThereIsNotPeriodOfEvaluationException;
import com.demodayapi.exceptions.ThereIsNotPeriodOfSubmissionException;
import com.demodayapi.exceptions.UserAlredyHasProjectCreatedException;
import com.demodayapi.exceptions.UserHasAlreadyRatedProjectException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.EvalRating;
import com.demodayapi.models.EvalRatingRequest;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.EvalRatingService;
import com.demodayapi.services.ProjectService;
import com.demodayapi.services.UserService;
import com.demodayapi.models.EvalRatingRequestList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.io.IOException;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@Validated
public class ProjectControler {

    @Autowired
    ProjectService projectService;

    @Autowired
    DemodayService demodayService;

    @Autowired
    UserService userService;

    @Autowired
    EvalRatingService evalRatingService;

    @PostMapping("/submitproject")
    public ResponseEntity<Project> postProject(@RequestParam(value ="description",required = false) String description,
            @RequestParam(value ="professor",required = false) String professor,
            @RequestParam(value ="linkvideo",required = false)String linkvideo,
            @RequestParam(value ="discipline",required = false)String discipline,
            @RequestParam(value ="period",required = false) Integer period,
            @RequestParam(value ="title",required = false) String title,
            @RequestParam(value ="tecnologies",required = false) String tecnologies,
            @RequestParam(value="linkdoc",required = false) String linkdoc,
            @RequestParam(value ="emails",required = false) String emails,
            @RequestParam(value= "type", required = false) String type,
            @RequestParam(value= "year", required = false) Year year,
            @RequestParam(value ="image", required = false) MultipartFile image,
            @RequestParam(value ="rejectionReason", required = false) String rejectionReason,
            HttpServletRequest request) {

            User user = userService.getLoggedUser(request);
            if (this.projectService.verifyIfUserHasProjectCreated(request) && !userService.isLoggedUserAdmin(request))
                throw new UserAlredyHasProjectCreatedException();
            Project newProject = new Project();
            newProject.setUser(user);
        try {
            DemodayStatusEnum demodayStatus = demodayService.verifyphase1InProgress();
            if (demodayStatus != DemodayStatusEnum.PHASE1)
                throw new ThereIsNotPeriodOfSubmissionException();
            Demoday demoday = demodayService.getDemodayWithBiggestValuePhase1();
            
            newProject.setTitle(title);
            newProject.setYear(year);
            newProject.setDescription(description);
            newProject.setProfessor(professor);
            newProject.setLinkvideo(linkvideo);
            newProject.setDiscipline(discipline);
            newProject.setTecnologies(tecnologies);
            newProject.setLinkdoc(linkdoc);
            newProject.setPeriod(period);
            newProject.setType(type);
            newProject.setRejectionReason(rejectionReason);
            if (emails !=null) {
            System.out.println("ENTROU IFF");
            List<String> emailList = Arrays.asList(emails.split(","));
            newProject.setEmails(emailList);
            }
           
            newProject.setDemoday(demoday);
            try {
                if (image != null)
                    newProject.setImage(image.getBytes());
            } catch (Exception e) {
                newProject.setImage(null);
            
            }
            Project savedProject = projectService.saveProject(newProject);
            return new ResponseEntity<>(savedProject, HttpStatus.CREATED);

        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }

    @GetMapping("/getallprojects")
    public ResponseEntity<List<Project>> getAllProjects() throws IOException, MethodArgumentNotValidException {

        List<Project> project = projectService.findAll();
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/getproject")
    public ResponseEntity<Project> getProject(@RequestParam(defaultValue = "id") int id)
            throws IOException, MethodArgumentNotValidException {

        Project project = projectService.findById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/getprojectbyuser")
    public ResponseEntity <List<Project>> getProjectbyuser(@RequestParam(defaultValue = "iduser") String iduser, HttpServletRequest request)
            throws IOException, MethodArgumentNotValidException {
            User user = userService.getLoggedUser(request);
            List<Project> project = projectService.listProjectsByUser(user.getId());
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/updateproject")
    public ResponseEntity<Project> updateProject(Project projectDetails, HttpServletRequest request) {
        try {
            DemodayStatusEnum demodayStatus = demodayService.verifyphase1InProgress();
            if (demodayStatus != DemodayStatusEnum.PHASE1)
                throw new ThereIsNotPeriodOfSubmissionException();
            User user = userService.getLoggedUser(request);
            Project existingProject = projectService.findById(projectDetails.getId());
            if (existingProject != null) {
                if (!existingProject.getUser().getCpf().equals(user.getCpf())
                        && !userService.isLoggedUserAdmin(request)) {
                    throw new RuntimeException("Usuário não autorizado a editar este projeto");
                }
                Project savedProject = this.projectService.updateProject(existingProject, projectDetails);
                return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }

    @GetMapping("/getdemodayprojects/{demoday_id}")
    public ResponseEntity<List<Project>> getProjectsByDemodayId(@PathVariable int demoday_id) {
        System.out.println(demoday_id);
        List<Project> projects = projectService.findByDemodayId(demoday_id);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/getdemodayacceptedprojects/{demoday_id}")
    public ResponseEntity<List<Project>> getAcceptedProjectsByDemodayId(@PathVariable int demoday_id) {

        List<Project> projects = projectService.findByDemodayIdAndStatus(demoday_id, ProjectStatusEnum.ACCEPTED);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @DeleteMapping("/deleteprojects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id, HttpServletRequest request) {
        if (!userService.isLoggedUserAdmin(request))
            throw new UserIsNotAdminException();
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pendingprojects")
    public ResponseEntity<List<Project>> getPendingProjects(HttpServletRequest request)
            throws IOException, MethodArgumentNotValidException {
        User user = userService.getLoggedUser(request);
        UserTypeEnum userType = user.getType();
        if (userType == UserTypeEnum.STUDENT)
            throw new UserIsNotAdminException();
        List<Project> projects = projectService.listOfPenddingProjects(ProjectStatusEnum.SUBMITTED);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/approveproject/{id}")
    public ResponseEntity<?> approveProject(@PathVariable int id, HttpServletRequest request) {
        try {
            User user = userService.getLoggedUser(request);
            if (user.getType() != UserTypeEnum.ADMIN) {
                throw new UserIsNotAdminException();
            }
            Project project = projectService.findById(id);
            if (project == null) {
                return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
            }
            projectService.approveProject(project);
            return new ResponseEntity<>("Project approved successfully", HttpStatus.OK);
        } catch (UserIsNotAdminException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rejectproject/{id}")
    public ResponseEntity<?> rejectProject(@PathVariable int id, @RequestBody Map<String, String> requestBody,
            HttpServletRequest request) {
        try {
            User user = userService.getLoggedUser(request);
            if (user.getType() == UserTypeEnum.STUDENT) {
                throw new UserIsNotAdminException();
            }
            Project project = projectService.findById(id);
            if (project == null) {
                return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
            }
            String rejectionReason = requestBody.get("rejectionReason");
            projectService.rejectProject(project, rejectionReason);
            return new ResponseEntity<>("Project rejected successfully", HttpStatus.OK);
        } catch (UserIsNotAdminException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/evaluateproject")
    public ResponseEntity<?> evaluateProject(@Valid @RequestBody EvalRatingRequestList evalRatingRequestList,
            HttpServletRequest request) {
        User loggedUser = this.userService.getLoggedUser(request);

        DemodayStatusEnum demodayStatus = demodayService.getDemodayStatus();
        if (demodayStatus != DemodayStatusEnum.PHASE3)
            throw new ThereIsNotPeriodOfEvaluationException();
        System.out.println(demodayStatus);

        if (this.evalRatingService.hasUserVotedProject(loggedUser.getId(), evalRatingRequestList.getProjectId())) {
            throw new UserHasAlreadyRatedProjectException();
        }

        System.out.println(demodayStatus);

        List<EvalRatingRequest> evalRartingRequests = evalRatingRequestList.getEvalRatings();
        System.out.println(demodayStatus);
        for (EvalRatingRequest evalRatingRequest : evalRartingRequests) {
            try {
                this.evalRatingService.createNewEvalRating(loggedUser.getId(), evalRatingRequestList.getProjectId(),
                        evalRatingRequest);
            } catch (Exception e) {
                this.evalRatingService.deleteEvalRatingsByUserAndProject(loggedUser.getId(),
                        evalRatingRequestList.getProjectId());
                if (e.getMessage().contains("Duplicate entry")) {
                    throw new DuplicateEvaluationByCriteriaException("Avaliação duplicada para o critério com id "
                            + Integer.toString(evalRatingRequest.getRate()) + ".");
                } else {
                    e.printStackTrace();
                    return new ResponseEntity<>("Erro Interno.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        }
        System.out.println(demodayStatus);
        return new ResponseEntity<>("Avaliação salva.", HttpStatus.OK);
    }

}
