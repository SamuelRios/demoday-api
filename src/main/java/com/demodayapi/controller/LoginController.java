package com.demodayapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.time.Duration;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demodayapi.services.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    FirebaseService firebaseService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam(defaultValue = "userToken") String userToken, HttpServletResponse requestResponse) throws IOException, MethodArgumentNotValidException {
        try {
            System.out.println(userToken);
            Cookie tokenCookie = this.firebaseService.createSessionCookie(userToken);
			Cookie tokenLocalCookie = this.firebaseService.createSessionLocalCookie(userToken);
            requestResponse.addCookie(tokenCookie);
			// EXCLUIR LOCAL QUANDO ENTRAR EM PRODUÇÃO
            requestResponse.addCookie(tokenLocalCookie);
            Map<String, Boolean> response = new HashMap<>();
            response.put("logged", true);
            return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
        } catch (FirebaseAuthException | IOException e) {
            System.out.println("AQUI ===============================================================");
            System.out.println(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@PostMapping("signingdois")
  public String demo (@RequestParam(defaultValue = "userToken") String userToken, HttpServletResponse response) {
	try {
	// Building cookies
		ResponseCookie cookie = ResponseCookie.from("session", this.firebaseService.createSessionToken(userToken)) // key & value
			.httpOnly(true)
			.secure(false)
			// .domain("localhost")  // host
			// .path("/")      // path
			.maxAge(Duration.ofHours(1))
			.sameSite("None")  // sameSite
			.build()
			;
		
		// Response to the client
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		
		return "ok";
	} catch (FirebaseAuthException | IOException e) {
		System.out.println("AQUI ===============================================================");
		System.out.println(e.getMessage());
		return "deu ruim";
	}
}

    @CrossOrigin()
	@GetMapping("/checkauthstatus")
	public ResponseEntity<?>  checkAuthStatus(HttpServletRequest request, HttpServletResponse requestResponse) {
		String sessionCookieValue = null;
		String userIdCookieValue = null;
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("session")) {
						sessionCookieValue = cookie.getValue();
					} else if (cookie.getName().equals("userId")) {
						userIdCookieValue = cookie.getValue();
					}
				}
			}
			if (sessionCookieValue != null && userIdCookieValue != null) {
				this.firebaseService.checkSessionCookie(sessionCookieValue);
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
