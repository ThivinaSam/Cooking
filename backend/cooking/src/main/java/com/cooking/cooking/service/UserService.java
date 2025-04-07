package com.cooking.cooking.service;

import com.cooking.cooking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    private FirebaseService firebaseService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String id = oauth2User.getName(); // Usually the subject claim from the JWT
            
            // Check if user exists in Firebase, if not, create them
            try {
                User user = firebaseService.getDocument("users", id, User.class);
                if (user == null) {
                    user = new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setName(name);
                    user.setCreatedAt(System.currentTimeMillis());
                    
                    firebaseService.createDocument("users", id, user);
                }
                return user;
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException("Error accessing user data", e);
            }
        }
        return null;
    }
}