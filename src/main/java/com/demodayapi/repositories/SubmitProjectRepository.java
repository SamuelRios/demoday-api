package com.demodayapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.SubmitProject;
import com.demodayapi.models.User;
import com.demodayapi.models.newDemoday;


@Repository
public interface SubmitProjectRepository extends CrudRepository<SubmitProject, Integer>{

    List<SubmitProject> findAll();
    SubmitProject findByUser(User user);
    SubmitProject findByDemoday(newDemoday demoday);
}