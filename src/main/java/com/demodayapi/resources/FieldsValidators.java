package com.demodayapi.resources;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class FieldsValidators {

    public static boolean validatePassword(String password) throws MethodArgumentNotValidException{
        if(password == null){
            BindingResult result = new BeanPropertyBindingResult(new Object(), "user");
            result.addError(new FieldError("user", "password", "A senha não pode estar em branco."));
            throw new MethodArgumentNotValidException(null, result);
        }
        if(password.length() < 6){
            BindingResult result = new BeanPropertyBindingResult(new Object(), "user");
            result.addError(new FieldError("user", "password", "A senha deve possuir pelo meno 6 caracteres."));
            throw new MethodArgumentNotValidException(null, result);
        }
        return true;
    }
}
