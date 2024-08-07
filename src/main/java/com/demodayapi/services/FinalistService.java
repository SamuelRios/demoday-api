package com.demodayapi.services;

import com.demodayapi.models.Finalist;
import com.demodayapi.repositories.FinalistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FinalistService {

    @Autowired
    private FinalistRepository finalistRepository;

    @Transactional
    public void deleteByProjectId(Integer projectId) {
        System.out.println("aqui!?");
        finalistRepository.deleteByProjectId(projectId);
    }

    public Finalist save(Finalist finalist) {
        return finalistRepository.save(finalist);
    }

    public boolean isProjectFinalist(int projectId){
        Finalist finalist = this.finalistRepository.findByProjectId(projectId);
        if(finalist != null){
            return true;
        }
        return false;
    }

    // Adicione outros métodos de serviço conforme necessário
}
