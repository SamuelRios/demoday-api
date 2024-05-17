package com.demodayapi.services;
import java.util.List;

import com.demodayapi.enums.ProjectStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.models.Demoday;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.repositories.ProjectRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;

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

  public Project findById(int id) {
    return this.projectRepository.findById(id);
  }

  public List<Project> projectsOfMostRecentDemoday() {
    return this.projectRepository.listProjectsOfMostRecentDemoday();
  }

  public Project updateProject(Project existingProject, Project projectDetails) {

    if (existingProject != null) {
        existingProject.setPeriod(projectDetails.getPeriod());
        existingProject.setTitle(projectDetails.getTitle());
        existingProject.setLinkvideo(projectDetails.getLinkvideo());
        existingProject.setDiscipline(projectDetails.getDiscipline());
        existingProject.setProfessor(projectDetails.getProfessor());
        existingProject.setYear(projectDetails.getYear());
        existingProject.setDescription(projectDetails.getDescription());
        existingProject.setCategory(projectDetails.getCategory());
        existingProject.setTecnologies(projectDetails.getTecnologies());
        existingProject.setLinkdoc(projectDetails.getLinkdoc());
        existingProject.setStatus(projectDetails.getStatus().toString());
        existingProject.setType(projectDetails.getType().toString());
        existingProject.setImage(projectDetails.getImage());
        //existingProject.setDemoday(projectDetails.getDemoday());
        //existingProject.setUser(projectDetails.getUser());

        return projectRepository.save(existingProject);
    } else {
        throw new RuntimeException("Projeto não encontrado");
    }
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

}





// public Project saveList(List <Project> criteriaList){
// for (Project criteria : criteriaList) {
// Project newCriteria = new Project();

// }
// return this.AccCriteriaRepository.saveAll(newCriteria);
// }
