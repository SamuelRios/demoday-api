package com.demodayapi.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.EvalCriteriaDemoday;
import com.demodayapi.repositories.EvalCriteriaDemodayRepository;



@Service
public class EvalCriteriaDemodayService {
    @Autowired
    private EvalCriteriaDemodayRepository evalCiteriaDemodayRepository;
  
    public EvalCriteriaDemoday saveEval(EvalCriteriaDemoday newEval){
        return this.evalCiteriaDemodayRepository.save(newEval);
   }
   }
   

//    public AccCriteriaDemoday saveList(List <AccCriteriaDemoday> criteriaList){
//     for (AccCriteriaDemoday criteria : criteriaList) {
//         AccCriteriaDemoday  newCriteria = new AccCriteriaDemoday();
        
        
//     }
//     return this.AccCriteriaRepository.saveAll(newCriteria);
// }



