package com.demodayapi.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.models.Demoday;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.repositories.ProjectRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private DemodayService demodayService;

  @Autowired
  private UserService userService;

  public Project saveProject(Project newProject) {
    newProject.setStatus("SUBMETIDO");
    return this.projectRepository.save(newProject);
  }

  public List<Project> findAll() {
    return this.projectRepository.findAll();
  }

  public List<Project> projectsOfMostRecentDemoday() {
    return this.projectRepository.listProjectsOfMostRecentDemoday();
  }

 

  public boolean verifyIfUserHasProjectCreated(HttpServletRequest request){
    List <Project> projectList = this.projectsOfMostRecentDemoday();
    
     
    User user = userService.getLoggedUser(request);
    System.out.println(user.getId());
    for (Project project : projectList) {
      if (project.getUser().getId().equals(user.getId())) {
          System.out.println("ENTROU AQUI");
          return true; // Se encontrar um projeto associado ao usuário retorna true
      }
  }
  return false; // Se não encontrar nenhum projeto associado ao usuário retorna false
}

}





// public Project saveList(List <Project> criteriaList){
// for (Project criteria : criteriaList) {
// Project newCriteria = new Project();

// }
// return this.AccCriteriaRepository.saveAll(newCriteria);
// }
