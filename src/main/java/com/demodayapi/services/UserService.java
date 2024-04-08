package com.demodayapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.models.User;
import com.demodayapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public User saveUser(User user){
        return this.userRepository.save(user);
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

}
