package com.demodayapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demodayapi.models.Demoday;

@Repository
public interface DemodayRepository extends CrudRepository<Demoday, Integer>{

    
    @SuppressWarnings("null")
    List<Demoday> findAll();
    Demoday findById(int Id);
    void deleteById(int Id);
    

    
   
}
