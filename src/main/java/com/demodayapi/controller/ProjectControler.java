package com.demodayapi.controller;
import com.demodayapi.models.Project;
import com.demodayapi.services.ProjectService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProjectControler {

    @Autowired
    ProjectService projectService;
 
 

    @PostMapping("/newproject")
    public ResponseEntity<Project> postDemoday(@RequestBody Project newProject) {
        try {
        
                Project savedProject = projectService.saveProject(newProject); 
            return new ResponseEntity<>(savedProject, HttpStatus.CREATED);

        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>( HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }


    
}
