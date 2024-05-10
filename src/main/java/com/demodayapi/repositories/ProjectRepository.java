package com.demodayapi.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.models.Demoday;


@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>{

    List<Project> findAll();
    Project findByUser(User user);
    Project findByDemoday(Demoday demoday);

 
}