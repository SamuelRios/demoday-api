package com.demodayapi.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.Project;
import com.demodayapi.repositories.ProjectRepository;



@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
  
    public Project saveCriteria(Project newCriteria){
        return this.projectRepository.save(newCriteria);
   }
  
}


   

//    public Project saveList(List <Project> criteriaList){
//     for (Project criteria : criteriaList) {
//         Project  newCriteria = new Project();
        
        
//     }
//     return this.AccCriteriaRepository.saveAll(newCriteria);
// }



