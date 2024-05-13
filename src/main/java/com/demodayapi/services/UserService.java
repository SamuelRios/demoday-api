package com.demodayapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.enums.UserStatusEnum;
import com.demodayapi.enums.UserTypeEnum;
import com.demodayapi.exceptions.UserNotLoggedException;
import com.demodayapi.models.User;
import com.demodayapi.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirebaseService firebaseService;

    public boolean existsEmail(String email){
        User existingUser = this.userRepository.findByEmail(email);
        if(existingUser != null){
            return true;
        }
        return false;
    }

    public boolean existsCPF(String cpf){
        User existingUser = this.userRepository.findByCpf(cpf);
        if(existingUser != null){
            return true;
        }
        return false;
    }

    public void setUserStatus(User user){
        if(user.getType().equals(UserTypeEnum.STUDENT)){
            System.out.println("eh estudante");
            user.setStatus("APPROVED");
        } else {
            System.out.println("eh professor");
            user.setStatus("PENDING");
        }
    }

    public User saveUser(User user){
        return this.userRepository.save(user);
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    public List<User> findAllPending(){
        return this.userRepository.findByStatus(UserStatusEnum.PENDING);
    }
    
    public User getLoggedUser(HttpServletRequest request){
        try{
            String userId = this.firebaseService.getLoggedUserId(request);
            // System.out.println(userId);
            if(userId != null)
                return this.userRepository.findById(userId).get();
            else throw new UserNotLoggedException();
        } catch (Exception e){
            throw new UserNotLoggedException();
        }
    }

    public Boolean isLoggedUserAdmin(HttpServletRequest request){
        User loggedUser = this.getLoggedUser(request);
        if(loggedUser.getType().equals(UserTypeEnum.ADMIN)) return true;
        else return false;
    }

}
