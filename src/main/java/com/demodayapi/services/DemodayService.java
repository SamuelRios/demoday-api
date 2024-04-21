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



 public  boolean validateExistFaseOne(Demoday demoday) {
    // Realizar validações aqui
    // Por exemplo:
    if (demoday.getPhaseOneInit() == null || demoday.getPhaseOneEnd() == null) {
        return true;
    }
    return false;
}


    public  boolean ValidateBiggestInitDate(Demoday demoday) {
        // Realizar validações aqui
        // Por exemplo:
        
        if (demoday.getPhaseOneInit().isBefore(demoday.getPhaseTwoInit()) && 
        demoday.getPhaseTwoInit().isBefore(demoday.getPhaseThreeInit()) && 
        demoday.getPhaseThreeInit().isBefore(demoday.getPhaseFourInit()) ) {
            return false; 
        }
        return true;
        }     


    public  boolean ValidateBiggestEndDate(Demoday demoday) {
            // Realizar validações aqui
            // Por exemplo:
            
            if (demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoEnd()) && 
            demoday.getPhaseTwoEnd().isBefore(demoday.getPhaseThreeEnd()) && 
            demoday.getPhaseThreeEnd().isBefore(demoday.getPhaseFourEnd())) {
            return false; 
        } 
            return true;
       }     

        public  boolean ValidateBiggestInitEndDate(Demoday demoday) {
            // Realizar validações aqui
            // Por exemplo:
            
            if (demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) && 
            demoday.getPhaseTwoEnd().isBefore(demoday.getPhaseThreeInit()) && 
            demoday.getPhaseThreeEnd().isBefore(demoday.getPhaseFourInit())) {
            return false; 
        } 
            return true;
       } 
    


}