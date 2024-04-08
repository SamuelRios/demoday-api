package com.demodayapi.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseClient {

    private FirebaseClient(){
        
    }

    private static FirebaseAuth firebaseInstance;

	private static String tokenFile = "src/main/resources/tokens/demoday-db-419519-firebase-adminsdk-1t357-742d3bddcb.json";

    public static FirebaseAuth getInstance() throws IOException{
        if(FirebaseClient.firebaseInstance == null){
            FirebaseClient.newInstance();
        }
        return FirebaseClient.firebaseInstance;
    }

    private static void newInstance() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            FirebaseClient.firebaseInstance = FirebaseAuth.getInstance();
        } else {
            System.out.println("aqui apenas uma vezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
            FileInputStream token = new FileInputStream(tokenFile);
    
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(token))
                .build();
    
            FirebaseApp.initializeApp(options);
            FirebaseClient.firebaseInstance = FirebaseAuth.getInstance();
        }
        
    }

}
