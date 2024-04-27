package com.demodayapi.firebase;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Service
public class FirebaseClient {

    private FirebaseAuth firebaseInstance;

    @Autowired
    ResourceLoader resourceLoader;

	private String tokenFile = "tokens/firebasetoken.json";

    private FirebaseClient() {
    }

    public FirebaseAuth getInstance() throws IOException{
        if(this.firebaseInstance == null){
            this.newInstance();
        }
        return this.firebaseInstance;
    }

    private void newInstance() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            this.firebaseInstance = FirebaseAuth.getInstance();
        } else {
            System.out.println("aqui apenas uma vezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
            Resource res = resourceLoader.getResource("classpath:" + this.tokenFile);
            InputStream token = res.getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(token))
                .build();
    
            FirebaseApp.initializeApp(options);
            this.firebaseInstance = FirebaseAuth.getInstance();
        }
        
    }
}
