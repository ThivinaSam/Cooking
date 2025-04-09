package com.cooking.cooking.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public <T> T getDocument(String collection, String id, Class<T> valueType) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection(collection).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        
        if (document.exists()) {
            return document.toObject(valueType);
        } else {
            return null;
        }
    }

    public String createDocument(String collection, Object data) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection(collection).document();
        ApiFuture<WriteResult> future = docRef.set(data);
        future.get();
        return docRef.getId();
    }

    public void createDocument(String collection, String id, Object data) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection(collection).document(id);
        ApiFuture<WriteResult> future = docRef.set(data);
        future.get(); // Wait for the operation to complete
    }

    public void updateDocument(String collection, String id, Map<String, Object> updates) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection(collection).document(id);
        ApiFuture<WriteResult> future = docRef.update(updates);
        future.get();
    }

    public void deleteDocument(String collection, String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getFirestore().collection(collection).document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        future.get();
    }

    public <T> List<T> getCollection(String collectionName, Class<T> valueType) throws ExecutionException, InterruptedException {
        List<T> items = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = getFirestore().collection(collectionName).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        
        for (QueryDocumentSnapshot document : documents) {
            T item = document.toObject(valueType);
            items.add(item);
        }
        
        return items;
    }

    public <T> List<T> queryDocuments(String collectionName, String field, String operator, 
                                      String value, Class<T> valueType) 
            throws ExecutionException, InterruptedException {
        
        List<T> items = new ArrayList<>();
        com.google.cloud.firestore.Query query = getFirestore().collection(collectionName);
        
        if ("==".equals(operator)) {
            query = query.whereEqualTo(field, value);
        } else if (">".equals(operator)) {
            query = query.whereGreaterThan(field, value);
        } else if (">=".equals(operator)) {
            query = query.whereGreaterThanOrEqualTo(field, value);
        } else if ("<".equals(operator)) {
            query = query.whereLessThan(field, value);
        } else if ("<=".equals(operator)) {
            query = query.whereLessThanOrEqualTo(field, value);
        }
        
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        
        for (QueryDocumentSnapshot document : documents) {
            T item = document.toObject(valueType);
            items.add(item);
        }
        
        return items;
    }
}
