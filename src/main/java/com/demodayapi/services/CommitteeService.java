package com.demodayapi.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.models.Committee;
import com.demodayapi.repositories.CommitteeRepository;

import jakarta.transaction.Transactional;
 



@Service
public class CommitteeService {
    @Autowired
  private CommitteeRepository committeeDemodayRepository;

            public Committee saveDemoday(Committee newCommittee) {
            Committee saveCommittee = this.committeeDemodayRepository.save(newCommittee);
            return this.committeeDemodayRepository.save(saveCommittee);
            }


@Transactional
    public void deleteCommitteeById(int id) {
    Committee project = committeeDemodayRepository.findById(id).orElse(null);
    if (project != null) {   // Excluir os e-mails associados ao projeto
        project.setDemoday(null); 
        committeeDemodayRepository.delete(project);
    }
}

}
 