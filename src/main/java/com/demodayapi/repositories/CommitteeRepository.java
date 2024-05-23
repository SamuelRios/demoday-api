
package com.demodayapi.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.Committee;



@Repository
public interface CommitteeRepository extends CrudRepository<Committee, Integer>{

    List <Committee> findAll();
    void deleteById(int Id);

    @Query("SELECT u FROM Committee u ORDER BY u.id DESC LIMIT 1")
    Committee getActiveCommittee();

 
  
}

