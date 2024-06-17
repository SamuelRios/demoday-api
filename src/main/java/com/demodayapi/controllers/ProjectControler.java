package com.demodayapi.controllers;

import com.demodayapi.enums.DemodayStatusEnum;
import com.demodayapi.enums.ProjectStatusEnum;
import com.demodayapi.exceptions.ThereIsNotPeriodOfSubmissionException;
import com.demodayapi.exceptions.UserAlredyHasProjectCreatedException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.UserNotLoggedException;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.ProjectService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProjectControler {

    @Autowired
    ProjectService projectService;

    @Autowired
    DemodayService demodayService;
    @Autowired
    UserService userService;

    @PostMapping("/submitproject")
    public ResponseEntity<Project> postProject(@RequestBody Project newProject, HttpServletRequest request) {
        try {
            DemodayStatusEnum demodayStatus = demodayService.verifyphase1InProgress();
            if (demodayStatus != DemodayStatusEnum.PHASE1)
                throw new ThereIsNotPeriodOfSubmissionException();
            Demoday demoday = demodayService.getDemodayWithBiggestValuePhase1();
            newProject.setDemoday(demoday);
            User user = userService.getLoggedUser(request);
            if (this.projectService.verifyIfUserHasProjectCreated(request) && !userService.isLoggedUserAdmin(request))
                throw new UserAlredyHasProjectCreatedException();
            newProject.setUser(user);
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

    @PostMapping("/evaluateproject/{id}")
    public ResponseEntity<Project> evaluateProject(@PathVariable int id,
            @RequestParam(required = false) String feedback,
            @RequestParam ProjectStatusEnum status, HttpServletRequest request) {
        // Check whether user logged
        User loggedUser = userService.getLoggedUser(request);
        if (loggedUser == null) {
            throw new UserNotLoggedException("You need to be logged in to evaluate a project.");
        }

        Project project = projectService.findById(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if logged user is different from project authors
        if (isUserAuthor(project, loggedUser)) {
            throw new RuntimeException("Users cannot evaluate their own projects.");
        }

        // Check if logged user already has an evaluation for this project
        if (projectService.hasUserEvaluatedProject(project.getId(), loggedUser.getId())) {
            throw new RuntimeException("You have already evaluated this project.");
        }

        // Update project status and optional feedback
        project.setStatus(status);
        if (feedback != null) {
            project.setFeedback(feedback);
        }

        // Create a new evaluation for the project (implementation depends on your
        // model)
        Evaluation newEvaluation = createEvaluation(project, loggedUser, status, feedback);

        // Save the updated project and the new evaluation
        projectService.saveProjectWithEvaluation(project, newEvaluation);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // Helper method to check if user is an author of the project
    private boolean isUserAuthor(Project project, User user) {
        // checar autor da avaliacao comparando com lista de autores
        return project.getUser().getId() == user.getId();
    }

    // Helper method to create a new evaluation (replace with your actual evaluation model)
    private Evaluation createEvaluation(Project project, User user, ProjectStatusEnum status, String feedback) {
        Evaluation evaluation = new Evaluation();
        evaluation.setProject(project);
        evaluation.setUser(user);
        evaluation.setStatus(status);
        evaluation.setFeedback(feedback);
        // ... set other evaluation fields as needed
        return evaluation;
    }

    @GetMapping("/getdemodayprojects/{demoday_id}")
    public ResponseEntity<List<Project>> getProjectsByDemodayId(@PathVariable int demoday_id) {
        System.out.println(demoday_id);
        List<Project> projects = projectService.findByDemodayId(demoday_id);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/getdemodayacceptedprojects/{demoday_id}")
    public ResponseEntity<List<Project>> getAcceptedProjectsByDemodayId(@PathVariable int demoday_id) {

        List<Project> projects = projectService.findByDemodayIdAndStatus(demoday_id, ProjectStatusEnum.ACEPTED);
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
    public ResponseEntity<List<Project>> getPendingUsers(HttpServletRequest request)
            throws IOException, MethodArgumentNotValidException {
        if (!userService.isLoggedUserAdmin(request))
            throw new UserIsNotAdminException();
        List<Project> projects = projectService.listOfPenddingProjects(ProjectStatusEnum.SUBMITTED);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

}
