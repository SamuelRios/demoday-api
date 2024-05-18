package com.demodayapi.services;
import java.util.List;

import com.demodayapi.enums.ProjectStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.repositories.ProjectRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;


  @Autowired
  private UserService userService;

  public Project saveProject(Project newProject) {
    newProject.setStatus("SUBMETIDO");
    return this.projectRepository.save(newProject);
  }

  public List<Project> findAll() {
    return this.projectRepository.findAll();
  }

  public Project findById(int id) {
    return this.projectRepository.findById(id);
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

  public List<Project> findSubmitted(){
    return projectRepository.findByStatus(ProjectStatusEnum.SUBMETIDO);
  }
  public List<Project> findAccepted(){
    return projectRepository.findByStatus(ProjectStatusEnum.ACEITO);
  }
@Transactional
public void deleteProjectById(int id) {
    Project project = projectRepository.findById(id).orElse(null);
    if (project != null) {   // Excluir os e-mails associados ao projeto
        project.setEmails(null); 
        projectRepository.delete(project);
    }
}
}





// public Project saveList(List <Project> criteriaList){
// for (Project criteria : criteriaList) {
// Project newCriteria = new Project();

// }
// return this.AccCriteriaRepository.saveAll(newCriteria);
// }
