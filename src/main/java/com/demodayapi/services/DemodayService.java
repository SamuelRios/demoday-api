package com.demodayapi.services;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.enums.DemodayStatusEnum;
import com.demodayapi.enums.ProjectStatusEnum;
import com.demodayapi.enums.UserTypeEnum;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.User;
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
    // System.out.println(demoday.getPhaseOneInit().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    // System.out.println(demoday.getPhaseOneEnd().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
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
       return true;
  } 
  
  public List<Demoday> getInProgressDemodays(){
    return this.demodayRepository.getInProgressDemodays();
  }
  public Boolean hasNotDemodayInProgress(){
    List<Demoday> demodayList = this.getInProgressDemodays();
    LocalDate dataAtual = LocalDate.now(); // Obtém a data atual
        for (Demoday demoday : demodayList) {
        if( demoday.getPhaseFourEnd() == null){
                System.out.println("demodayInativo");
                return false;  
            }else{
               if ((demoday.getPhaseFourEnd().isAfter(dataAtual))) {
                return false;
               }
             }
        }
        System.out.println("demodayAativo");
        return true;
    }


    public List<Demoday> getPhase1(){ 
        return this.demodayRepository.getPhase1();
    }
    public DemodayStatusEnum verifyphase1InProgress(){
      
       
        List<Demoday> demodayList = this.getPhase1();
        System.out.println(demodayList.size());
        LocalDate dataAtual = LocalDate.now(); // Obtém a data atual
        for (Demoday demoday : demodayList) { 
            System.out.println("ENTROU NO LOOP");
            if( demoday.getPhaseOneInit().isEqual(dataAtual) || demoday.getPhaseOneInit().isBefore(dataAtual) &&
                demoday.getPhaseOneEnd().isAfter(dataAtual) ||
                demoday.getPhaseOneEnd().isEqual(dataAtual)){
                    return DemodayStatusEnum.PHASE1;
                } 
            }


        
        
        return DemodayStatusEnum.FINISHED;  
}
}


