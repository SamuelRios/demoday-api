package com.demodayapi.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demodayapi.models.Demoday;

@Repository
public interface DemodayRepository extends CrudRepository<Demoday, Integer>{

    
    @SuppressWarnings("null")
    List<Demoday> findAll();
    Demoday findById(int Id);
    void deleteById(int Id);

    @Query("select u from demoday u where u.demoday =?1" )
        List<Demoday> teste();
    

    
   
}
