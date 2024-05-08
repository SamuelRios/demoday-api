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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class FirebaseService {

    @Autowired
    private FirebaseClient firebaseClient;

    @Value("${demoday.domain}")
    private String domain;
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


    public String createSessionToken(String userToken) throws FirebaseAuthException, IOException{
        System.out.println(this.domain);
        SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(this.expiresIn).build();
        return this.firebaseClient.getInstance().createSessionCookie(userToken, options);
    }

    public Cookie createSessionCookie(String userToken) throws FirebaseAuthException, IOException{
        System.out.println(this.domain);
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

    public Cookie createSessionLocalCookie(String userToken) throws FirebaseAuthException, IOException{
        // EXCLUIR METODO QUANDO ENTRAR EM PRODUCÃO
        System.out.println(this.domain);
        SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(this.expiresIn).build();
        String sessionCookie = this.firebaseClient.getInstance().createSessionCookie(userToken, options);
        Cookie tokenCookie = new Cookie("session", sessionCookie);
        tokenCookie.setMaxAge(36000);
        tokenCookie.setSecure(true);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setDomain("localhost");
        tokenCookie.setPath("/");
        return tokenCookie;
    }

    public String checkSessionCookie(String sessionCookieValue) throws FirebaseAuthException, IOException{
        FirebaseToken decodedToken = this.firebaseClient.getInstance().verifySessionCookie(sessionCookieValue);
        String uid = decodedToken.getUid();
        return uid;
    }

    public HttpServletResponse setSessionCookieNull(HttpServletResponse requestResponse){
        Cookie tokenCookie = new Cookie("session", null);
        tokenCookie.setMaxAge(0);
        tokenCookie.setSecure(false);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setDomain(this.domain);
        tokenCookie.setPath("/");
        requestResponse.addCookie(tokenCookie);
        return requestResponse;
    }
    
    public String getLoggedUserId(HttpServletRequest request){
        String sessionCookieValue = null;
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("session")) {
						sessionCookieValue = cookie.getValue();
					}
				}
			}
			if (sessionCookieValue != null) {
				return this.checkSessionCookie(sessionCookieValue);
			} else {
				return null;
			}
		} catch (Exception e) {
            return null;
		}
        
    }


}
