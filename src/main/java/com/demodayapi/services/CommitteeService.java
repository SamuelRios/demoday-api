package com.demodayapi.services;

import com.demodayapi.models.Committee;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.User;
import com.demodayapi.repositories.CommitteeRepository;
import com.demodayapi.repositories.DemodayRepository;
import com.demodayapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class CommitteeService {
    @Autowired
  private CommitteeRepository committeeDemodayRepository;

    @Autowired
    private DemodayRepository demodayRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Transactional
    public Committee addDemodayAndUsers(int demodayId, List<String> userIds) {
        Demoday demoday = demodayRepository.findById(demodayId);
        Iterable<User> usersIterable = userRepository.findAllById(userIds);
        List<User> users = StreamSupport.stream(usersIterable.spliterator(), false)
                .collect(Collectors.toList());

        Committee committee = new Committee();
        committee.setDemoday(demoday);
        committee.setUsers(users);

        return committeeDemodayRepository.save(committee);
    }


    public List<Committee> getAllCommittees() {
        return committeeDemodayRepository.findAll();
    }

}
 