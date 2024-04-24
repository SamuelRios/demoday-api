package com.demodayapi.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demodayapi.exceptions.UserCPFAlreadyExistsException;
import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.models.User;
import com.demodayapi.services.FirebaseService;
import com.demodayapi.services.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController

@CrossOrigin
public class DemodayApiController {

    @Autowired
    UserService userService;

    @Autowired
    FirebaseService firebaseService;

	@Value("${demoday.domain}")
    private String domain = "localhost";

    @GetMapping("/")
    public String  hello(){
        return "demoday-api is online";
    }

    @PostMapping("/createuser")
    public ResponseEntity<Map<String,String>> postMethodName(@Valid @RequestBody User user) throws IOException, MethodArgumentNotValidException {
        try {
            if(userService.existsEmail(user.getCpf())) throw new UserEmailAlreadyExistsException();
            if(userService.existsCPF(user.getCpf())) throw new UserCPFAlreadyExistsException();
            String userId = this.firebaseService.createUser(user);
            user.setId(userId);
            User savedUser = userService.saveUser(user);
            if(savedUser != null){
                Map<String, String> response = new HashMap<>();
                response.put("userId", userId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 
        catch (FirebaseAuthException e) {
            System.out.println("AQUI ===============================================================");
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam(defaultValue = "userToken") String userToken, @RequestParam(defaultValue = "userId") String userId, HttpServletResponse requestResponse) throws IOException, MethodArgumentNotValidException {
				try {
                    Cookie tokenCookie = this.firebaseService.createSessionCookie(userToken);
                    requestResponse.addCookie(tokenCookie);
                    Cookie uidToken = this.firebaseService.createUIdToken(userId);
                    requestResponse.addCookie(uidToken);
                    Map<String, Boolean> response = new HashMap<>();
                    response.put("logged", true);
                    return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
                } catch (FirebaseAuthException | IOException e) {
                    System.out.println("AQUI ===============================================================");
                    System.out.println(e.getMessage());
                    return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
				this.firebaseService.setIdAndSessionCookieNull(requestResponse);
				Map<String, Boolean> response = new HashMap<>();
				response.put("logged", false);
				return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			this.firebaseService.setIdAndSessionCookieNull(requestResponse);
			Map<String, Boolean> response = new HashMap<>();
			response.put("logged", false);
			return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.OK);
		}
	}


    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getusers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    
    
    
}
