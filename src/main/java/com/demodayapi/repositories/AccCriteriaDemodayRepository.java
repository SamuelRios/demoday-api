package com.demodayapi.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demodayapi.models.AccCriteriaDemoday;


@Repository
public interface AccCriteriaDemodayRepository extends CrudRepository<AccCriteriaDemoday, Integer>{
        
    @SuppressWarnings("null")
    List<AccCriteriaDemoday> findAll();
    AccCriteriaDemoday findById(int Id);
    void deleteById(int Id);
    
  
}


    

