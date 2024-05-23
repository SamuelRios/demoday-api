package com.demodayapi.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.models.Committee;
import com.demodayapi.models.CommitteeUser;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.User;
import com.demodayapi.repositories.CommitteeRepository;
import com.demodayapi.repositories.CommitteeUserRepository;

import jakarta.transaction.Transactional;

@Service
public class CommitteeUserService {

@Autowired
 private CommitteeUserRepository committeeUserRepository;
    

 
    
   public List<CommitteeUser> allUsersActiveCommittee(int demoday_id) {
        return this.committeeUserRepository.listUsersActiveCommittee(demoday_id);
    }

   public CommitteeUser saveUserCommittee(CommitteeUser newUserCommittee) {
            CommitteeUser saveUserCommittee = this.committeeUserRepository.save(newUserCommittee);
            return this.committeeUserRepository.save(saveUserCommittee);
            }

    public List<CommitteeUser> findAll() {
    return this.committeeUserRepository.findAll();
    }


    @Transactional
    public void deleteAllCommitteeUsers(int demodayId) {
       committeeUserRepository.deleteAllCommitteeUsers(demodayId);
      }

      public void deleteById(String Id) {
        committeeUserRepository.deleteByIdUser(Id);
       }
 

    // @Transactional
    // public void deleteCommitteeUsersById(int idCommitee) {
   
    //     committeeUserRepository.deleteAllCommitteeUsers(idCommitee);
    // }
  


}
