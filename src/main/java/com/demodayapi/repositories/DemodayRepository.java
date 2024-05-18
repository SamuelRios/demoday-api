package com.demodayapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.Demoday;

@Repository
public interface DemodayRepository extends CrudRepository<Demoday, Integer> {

    List<Demoday> findAll();

    Demoday findById(int Id);

    void deleteById(int Id);

    @Query("SELECT u FROM Demoday u WHERE u.phaseFourEnd IS NULL ")
    List<Demoday> getDemodayphaseFourNull();

    @Query("SELECT u FROM Demoday u WHERE u.phaseFourEnd IS NOT NULL ORDER BY u.phaseFourEnd DESC LIMIT 1 ")
    List<Demoday> getDemodayphaseFourNotNull();

    @Query("SELECT u FROM Demoday u ORDER BY u.phaseOneInit DESC LIMIT 1")
    Demoday getPhase1();

}
