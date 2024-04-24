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
        if (newDemoday != null){
            Demoday savedDemoday= this.demodayRepository.save(newDemoday);
         return this.demodayRepository.save(savedDemoday);
         
        }
        return null;
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
    if (demoday.getPhaseOneInit() == null || demoday.getPhaseOneEnd() == null) {
        return true;
    }
    return false;
}

        public  boolean ValidateBiggestInitEndDate(Demoday demoday) {
    
       if (demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd()) && demoday.getPhaseTwoEnd() == null && 
       demoday.getPhaseFourEnd() == null && demoday.getPhaseFourEnd() == null){
        return false;
        }else{
                if (demoday.getPhaseTwoEnd() != null && 
                demoday.getPhaseThreeEnd() == null &&
                demoday.getPhaseTwoInit() != null && 
                demoday.getPhaseThreeInit() == null &&
                demoday.getPhaseFourInit() == null &&
                demoday.getPhaseFourEnd() == null &&
                demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd()) &&
                demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) &&
                demoday.getPhaseTwoInit().isBefore(demoday.getPhaseTwoEnd()) ){
                   
                    return false;
                }
                if(demoday.getPhaseTwoEnd() != null && demoday.getPhaseThreeEnd() != null && 
                demoday.getPhaseFourEnd() == null &&
                demoday.getPhaseTwoInit() != null && 
                demoday.getPhaseThreeInit() != null && 
                demoday.getPhaseFourInit() == null &&
                demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd())&&
                demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) && 
                demoday.getPhaseTwoInit().isBefore(demoday.getPhaseTwoEnd()) &&
                demoday.getPhaseTwoEnd().isBefore(demoday.getPhaseThreeInit()) &&
                demoday.getPhaseThreeInit().isBefore(demoday.getPhaseThreeEnd())){
                
                return false;
             }
                if(demoday.getPhaseTwoEnd() != null &&
                demoday.getPhaseThreeEnd() != null &&
                demoday.getPhaseFourEnd() != null &&
                demoday.getPhaseTwoInit() != null && 
                demoday.getPhaseThreeInit() != null  &&
                demoday.getPhaseFourInit() != null &&
                demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd())&&
                demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) && 
                demoday.getPhaseTwoInit().isBefore(demoday.getPhaseTwoEnd()) &&
                demoday.getPhaseTwoEnd().isBefore(demoday.getPhaseThreeInit()) &&
                demoday.getPhaseThreeInit().isBefore(demoday.getPhaseThreeEnd())&&
                demoday.getPhaseThreeEnd().isBefore(demoday.getPhaseFourInit()) &&
                demoday.getPhaseFourInit().isBefore(demoday.getPhaseFourEnd())){
                
                return false;
             }

        }
        System.out.println("Nenhum loop");
       return true;
  } 

}

