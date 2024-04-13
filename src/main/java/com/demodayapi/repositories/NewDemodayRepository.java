package com.demodayapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demodayapi.models.newDemoday;

@Repository
public interface NewDemodayRepository extends CrudRepository<newDemoday, Integer>{

    
    @SuppressWarnings("null")
    List<newDemoday> findAll();
    newDemoday findById(int Id);
    void deleteById(int Id);


    
   
}
