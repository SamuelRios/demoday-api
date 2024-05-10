package com.demodayapi.services;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    private int cookieTimeInHours = 10;

    @Value("${demoday.domain}")
    private String domain;

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
        SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(this.expiresIn).build();
        return this.firebaseClient.getInstance().createSessionCookie(userToken, options);
    }

    public ResponseCookie createSessionCookie(String userToken) throws FirebaseAuthException, IOException{
        return ResponseCookie.from("session", this.createSessionToken(userToken)) // key & value
			.httpOnly(true)
			.secure(true)
			.domain(this.domain)
			.path("/")
			.maxAge(Duration.ofHours(this.cookieTimeInHours))
			.sameSite("None")
			.build()
			;
    }
    
    public String checkSessionCookie(String sessionCookieValue) throws FirebaseAuthException, IOException{
        FirebaseToken decodedToken = this.firebaseClient.getInstance().verifySessionCookie(sessionCookieValue);
        String uid = decodedToken.getUid();
        return uid;
    }

    public HttpServletResponse setSessionCookieNull(HttpServletResponse requestResponse){
        ResponseCookie responseCookie = ResponseCookie.from("session", null)
			.httpOnly(true)
			.secure(true)
			// .domain("localhost")
			// .path("/")
			.maxAge(Duration.ofHours(0))
			.sameSite("None")
			.build()
			;
        requestResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return requestResponse;
    }
    
    public String getLoggedUserId(HttpServletRequest request){
        String sessionCookieValue = null;
		try {
			Cookie[] cookies = request.getCookies();
            System.out.println(cookies.length);
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("session")) {
						sessionCookieValue = cookie.getValue();
					}
				}
			}
			if (sessionCookieValue != null) {
                System.out.println("AQUI O COOKIE SESSAO valor:");
                System.out.println(sessionCookieValue);
				return this.checkSessionCookie(sessionCookieValue);
			} else {
                System.out.println("SEM SESSAO");
				return null;
			}
		} catch (Exception e) {
            return null;
		}
        
    }


}
