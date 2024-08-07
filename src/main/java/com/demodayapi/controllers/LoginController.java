package com.demodayapi.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demodayapi.models.User;
import com.demodayapi.services.FirebaseService;
import com.demodayapi.services.UserService;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    FirebaseService firebaseService;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam(defaultValue = "userToken") String userToken, HttpServletResponse requestResponse) throws IOException, MethodArgumentNotValidException {
        try {
            System.out.println(userToken);
            ResponseCookie cookie = this.firebaseService.createSessionCookie(userToken);
			requestResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			User loggedUser = this.userService.getLoggedUser(cookie.getValue());
			this.userService.checkUserStatus(loggedUser);
            return new ResponseEntity<User>(loggedUser, HttpStatus.OK);
        } catch (FirebaseAuthException | IOException e) {
            System.out.println("AQUI ===============================================================");
            System.out.println(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PostMapping("/signout")
    public ResponseEntity<?> signout(HttpServletResponse requestResponse) {
		this.firebaseService.setSessionCookieNull(requestResponse);
		return new ResponseEntity<>("ok", HttpStatus.OK);
        
    }

	@GetMapping("/checkauthstatus")
	public ResponseEntity<?>  checkAuthStatus(HttpServletRequest request, HttpServletResponse requestResponse) {
		String sessionToken = null;
		String userIdCookieValue = null;
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("session")) {
						sessionToken = cookie.getValue();
					} else if (cookie.getName().equals("userId")) {
						userIdCookieValue = cookie.getValue();
					}
				}
			}
			if (sessionToken != null && userIdCookieValue != null) {
				this.firebaseService.checkSessionToken(sessionToken);
				Map<String, Boolean> response = new HashMap<>();
				response.put("logged", true);
				return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
			} else {
				this.firebaseService.setSessionCookieNull(requestResponse);
				Map<String, Boolean> response = new HashMap<>();
				response.put("logged", false);
				return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			this.firebaseService.setSessionCookieNull(requestResponse);
			Map<String, Boolean> response = new HashMap<>();
			response.put("logged", false);
			return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
		}
	}
}
