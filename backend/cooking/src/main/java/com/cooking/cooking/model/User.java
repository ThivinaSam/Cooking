package com.cooking.cooking.model;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;

public class User {
    private String id;
    private String email;
    private String name;
    private List<String> roles;
    private List<String> preferences;
    private long createdAt;
    
    // Getters, setters, constructors
    // ...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    // public void createDocument(String collection, String id, Object data) throws ExecutionException, InterruptedException {
    //     DocumentReference docRef = getFirestore().collection(collection).document(id);
    //     ApiFuture<WriteResult> future = docRef.set(data);
    //     future.get();
    // }
}