package com.demodayapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demodayapi.models.Demoday;

@Repository
public interface DemodayRepository extends CrudRepository<Demoday, Integer>{
    
    List<Demoday> findAll();
    Demoday findById(int Id);
    void deleteById(int Id);


    @Query("SELECT u FROM Demoday u WHERE u.phaseFourEnd IS NULL OR u.phaseFourEnd = (SELECT MAX(u2.phaseFourEnd) FROM Demoday u2 WHERE u2.phaseFourEnd IS NOT NULL)")
    List<Demoday> getInProgressDemodays();
   
}
