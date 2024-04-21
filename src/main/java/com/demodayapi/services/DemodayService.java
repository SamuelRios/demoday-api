package com.demodayapi.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.Demoday;
import com.demodayapi.repositories.DemodayRepository;

@Service
public class DemodayService {

    @Autowired
    private DemodayRepository demodayRepository;

    public Demoday saveDemoday(Demoday newDemoday){
         return this.demodayRepository.save(newDemoday);

    }

    public List<Demoday> findAll(){
        return this.demodayRepository.findAll();
    }

    public boolean existsDemoday(int id){
        Demoday existingDemoday = this.demodayRepository.findById(id);
        if(existingDemoday != null){
            return true;
        }
        return false;
    }

   
	


}
