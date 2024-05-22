package com.demodayapi.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.enums.UserStatusEnum;
import com.demodayapi.enums.UserTypeEnum;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.UserNotFoundException;
import com.demodayapi.exceptions.UserNotLoggedException;
import com.demodayapi.exceptions.UserPedingException;
import com.demodayapi.exceptions.UserRejectedException;
import com.demodayapi.models.User;
import com.demodayapi.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
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

    public void setCreatedUserStatus(User user){
        if (user.getType()==UserTypeEnum.ADMIN) throw new UserIsNotAdminException();  //protege rota contra criação de admin

        if(user.getType().equals(UserTypeEnum.STUDENT)){
            System.out.println("eh estudante");
            user.setStatus("APPROVED");
        } else {
            System.out.println("eh professor");
            user.setStatus("PENDING");
        }
    }

    public User setUserStatus(String userId, String status){
        try{
            User user = this.userRepository.findById(userId).get();
            user.setStatus(status);
            return this.userRepository.save(user);
        } catch (NoSuchElementException e){
            throw new UserNotFoundException();
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
    public List<User> findAllProfessor(){
        return this.userRepository.findByType(UserTypeEnum.PROFESSOR);
    }
    
    public User getLoggedUser(HttpServletRequest request){
        try{
            String userId = this.firebaseService.getLoggedUserId(request);
            // System.out.println(userId);
            if(userId != null){
                return this.userRepository.findById(userId).get();

            }
            else throw new UserNotLoggedException();
        } catch (Exception e){
            throw new UserNotLoggedException();
        }
    }

    public User getLoggedUser(String sessionToken){
        try{
            String userId = this.firebaseService.getLoggedUserId(sessionToken);
            // System.out.println(userId);
            if(userId != null)
                return this.userRepository.findById(userId).get();
            else throw new UserNotLoggedException();
        } catch (Exception e){
            throw new UserNotLoggedException();
        }
    }

    public User getUserById(String userId){
        try{
            return this.userRepository.findById(userId).get();
        } catch (Exception e){
            throw new UserNotFoundException();
        }
    }

    public Boolean isLoggedUserAdmin(HttpServletRequest request){
        User loggedUser = this.getLoggedUser(request);
        if(loggedUser.getType().equals(UserTypeEnum.ADMIN)) return true;
        else return false;
    }


    public void checkUserStatus(User user){
        UserStatusEnum userStataus = user.getStatus();
        if(userStataus.equals(UserStatusEnum.PENDING)) throw new UserPedingException();
        if(userStataus.equals(UserStatusEnum.REJECTED)) throw new UserRejectedException();
    }

}
