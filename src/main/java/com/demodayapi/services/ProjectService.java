package com.demodayapi.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.Project;
import com.demodayapi.repositories.ProjectRepository;



@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
  
    public Project saveProject(Project newProject){
        newProject.setStatus("SUBMETIDO");
        return this.projectRepository.save(newProject);
   }

    public List<Project> findAll(){
        return this.projectRepository.findAll();
    }

      
  
}


   

//    public Project saveList(List <Project> criteriaList){
//     for (Project criteria : criteriaList) {
//         Project  newCriteria = new Project();
        
        
//     }
//     return this.AccCriteriaRepository.saveAll(newCriteria);
// }



