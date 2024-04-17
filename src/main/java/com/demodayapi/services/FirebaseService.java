package com.demodayapi.services;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.firebase.FirebaseClient;
import com.demodayapi.models.User;
import com.demodayapi.resources.FieldsValidators;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.SessionCookieOptions;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class FirebaseService {

    @Autowired
    private FirebaseClient firebaseClient;

    @Value("${demoday.domain}")
    private String domain = "localhost";
    // private String domain = "localhost:3200";

    private Long expiresIn = TimeUnit.DAYS.toMillis(1);

    public String createUser(User user) throws FirebaseAuthException, IOException, MethodArgumentNotValidException{
        FieldsValidators.validatePassword(user.getPassword());
        if(!this.isEmailRegistered(user.getEmail())){
            CreateRequest request = new CreateRequest()
            .setEmail(user.getEmail())
            .setEmailVerified(false)
            .setPassword(user.getPassword())
            // .setPhoneNumber("+11234567890")
            .setDisplayName(user.getName())
            // .setPhotoUrl("http://www.example.com/12345678/photo.png")
            .setDisabled(false);
            UserRecord userRecord = this.firebaseClient.getInstance().createUser(request);
            return userRecord.getUid();
        } else throw new UserEmailAlreadyExistsException();
    }

    public boolean isEmailRegistered(String email) throws IOException, FirebaseAuthException {
        try {
            this.firebaseClient.getInstance().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            if ("NOT_FOUND".equals(e.getErrorCode().toString())) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }

    public Cookie createUIdToken(String uId){
        Cookie uIdCookie = new Cookie("userId", uId);
        uIdCookie.setMaxAge(36000);
        uIdCookie.setSecure(true);
        uIdCookie.setHttpOnly(true);
        uIdCookie.setDomain(this.domain);
        uIdCookie.setPath("/");
        return uIdCookie;
    }

    public Cookie createSessionCookie(String userToken) throws FirebaseAuthException, IOException{
        SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(this.expiresIn).build();
        String sessionCookie = this.firebaseClient.getInstance().createSessionCookie(userToken, options);
        Cookie tokenCookie = new Cookie("session", sessionCookie);
        tokenCookie.setMaxAge(36000);
        tokenCookie.setSecure(true);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setDomain(this.domain);
        tokenCookie.setPath("/");
        return tokenCookie;
    }

    public FirebaseToken checkSessionCookie(String sessionCookieValue) throws FirebaseAuthException, IOException{
        FirebaseToken decodedToken = this.firebaseClient.getInstance().verifySessionCookie(sessionCookieValue);
        return decodedToken;
    }

    public HttpServletResponse setIdAndSessionCookieNull(HttpServletResponse requestResponse){
        Cookie tokenCookie = new Cookie("session", null);
        tokenCookie.setMaxAge(0);
        tokenCookie.setSecure(true);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setDomain(this.domain);
        tokenCookie.setPath("/");
        requestResponse.addCookie(tokenCookie);

        Cookie uIdCookie = new Cookie("userId", null);
        uIdCookie.setMaxAge(0);
        uIdCookie.setSecure(true);
        uIdCookie.setHttpOnly(true);
        uIdCookie.setDomain(this.domain);
        uIdCookie.setPath("/");
        requestResponse.addCookie(uIdCookie);
        return requestResponse;
    }


}
