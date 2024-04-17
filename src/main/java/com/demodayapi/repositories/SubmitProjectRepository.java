package com.demodayapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.SubmitProject;


@Repository
public interface SubmitProjectRepository extends CrudRepository<SubmitProject, Integer>{

    List<SubmitProject> findAll();
    SubmitProject findByUser(String user);
    SubmitProject findByDemoday(String demoday);
}