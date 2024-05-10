package com.demodayapi.repositories;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.AccCriteriaDemoday;
import com.demodayapi.models.EvalCriteriaDemoday;


@Repository
public interface EvalCriteriaDemodayRepository extends CrudRepository<EvalCriteriaDemoday, Integer>{
        
  
    List <EvalCriteriaDemoday> findAll();
    AccCriteriaDemoday findById(int Id);
    void deleteById(int Id);
    
  
}


    

