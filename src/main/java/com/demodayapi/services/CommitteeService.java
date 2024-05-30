package com.demodayapi.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.Committee;
import com.demodayapi.repositories.CommitteeRepository;

import jakarta.transaction.Transactional;


@Service
public class CommitteeService {
    @Autowired
  private CommitteeRepository committeeRepository;

            public Committee saveCommittee(Committee newCommittee) {
            Committee saveCommittee = this.committeeRepository.save(newCommittee);
            return this.committeeRepository.save(saveCommittee);
            }


@Transactional
    public void deleteCommitteeById(int id) {
    Committee project = committeeRepository.findById(id).orElse(null);
    if (project != null) {   // Excluir os e-mails associados ao projeto
         
        committeeRepository.delete(project);
    }
}


public Committee activeCommittee() {
    return committeeRepository.getActiveCommittee();
    }

}
 