package com.demodayapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.demodayapi.models.User;
import com.demodayapi.repositories.UserRepository;

public class UserService {

    @Autowired
    private static UserRepository userRepository;

    public static boolean existsEmail(String email){
        User existingUser = UserService.userRepository.findByEmail(email);
        if(existingUser != null){
            return true;
        }
        return false;
    }

    public static boolean existsCPF(String cpf){
        User existingUser = UserService.userRepository.findByCpf(cpf);
        if(existingUser != null){
            return true;
        }
        return false;
    }

    public static User saveUser(User user){
        return UserService.userRepository.save(user);
    }

    public static List<User> findAll(){
        return UserService.userRepository.findAll();
    }

}
