package com.demodayapi.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.newDemoday;
import com.demodayapi.repositories.NewDemodayRepository;

@Service
public class NewDemodayService {

    @Autowired
    private NewDemodayRepository demodayRepository;

    public newDemoday saveDemoday (newDemoday demoday){
        return this.demodayRepository.save(demoday);

    }

    public List<newDemoday> findAll(){
        return this.demodayRepository.findAll();
    }

    public boolean existsDemoday(int id){
        newDemoday existingDemoday = this.demodayRepository.findById(id);
        if(existingDemoday != null){
            return true;
        }
        return false;
    }


}
