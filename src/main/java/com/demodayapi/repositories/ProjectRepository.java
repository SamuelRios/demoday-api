package com.demodayapi.repositories;
import java.util.List;

import com.demodayapi.enums.ProjectStatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.models.Demoday;


@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>{

    List<Project> findAll();

    Project findById(int id);

    Project findByUser(User user);
    
    Project findByDemoday(Demoday demoday);

    @Query("SELECT p FROM Project p WHERE p.demoday.id = (SELECT MAX(d.id) FROM Demoday d) ")
     List<Project> listProjectsOfMostRecentDemoday();

    List<Project> findByStatus(ProjectStatusEnum status);

    @Query("SELECT p FROM Project p WHERE p.user.id =:iduser")
    List<Project> listProjectsOfUser(@Param("iduser") String iduser);


    @Query("SELECT p FROM Project p WHERE p.user.id = :iduser AND p.demoday.id = :demoday")
    Project currentProjectByuser(@Param("iduser") String iduser, @Param("demoday") int demoday);


    List<Project> findByDemodayId(int demodayId);

    List<Project> findByDemodayIdAndStatus(int demodayId, ProjectStatusEnum status);

    @Query("SELECT p  FROM Project p WHERE p.status = :status AND p.demoday.id = :demoday")
    List<Project> listProjectsStatusIsSubmitted(@Param("status") ProjectStatusEnum status,  @Param("demoday") int demoday);
    

}