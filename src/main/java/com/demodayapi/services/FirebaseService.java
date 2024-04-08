package com.demodayapi.services;

import java.io.IOException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.firebase.FirebaseClient;
import com.demodayapi.models.User;
import com.demodayapi.resources.FieldsValidators;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

public class FirebaseService {

    public static String createUser(User user) throws FirebaseAuthException, IOException, MethodArgumentNotValidException{
        FieldsValidators.validatePassword(user.getPassword());
        if(!FirebaseService.isEmailRegistered(user.getEmail())){
            CreateRequest request = new CreateRequest()
            .setEmail(user.getEmail())
            .setEmailVerified(false)
            .setPassword(user.getPassword())
            // .setPhoneNumber("+11234567890")
            .setDisplayName(user.getName())
            // .setPhotoUrl("http://www.example.com/12345678/photo.png")
            .setDisabled(false);
            UserRecord userRecord = FirebaseClient.getInstance().createUser(request);
            return userRecord.getUid();
        } else throw new UserEmailAlreadyExistsException();
    }

    public static boolean isEmailRegistered(String email) throws IOException, FirebaseAuthException {
        try {
            FirebaseClient.getInstance().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            if ("NOT_FOUND".equals(e.getErrorCode().toString())) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }


}
