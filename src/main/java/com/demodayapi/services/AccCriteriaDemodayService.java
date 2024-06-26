package com.demodayapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.AccCriteriaDemoday;
import com.demodayapi.repositories.AccCriteriaDemodayRepository;



@Service
public class AccCriteriaDemodayService {
    @Autowired
    private AccCriteriaDemodayRepository AccCriteriaRepository;
  
    public AccCriteriaDemoday saveCriteria(AccCriteriaDemoday newCriteria){
        return this.AccCriteriaRepository.save(newCriteria);
   }
   }
 