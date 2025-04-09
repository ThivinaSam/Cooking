package com.cooking.cooking.controller;

import com.cooking.cooking.model.Recipe;
import com.cooking.cooking.model.User;
import com.cooking.cooking.service.FirebaseService;
import com.cooking.cooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private FirebaseService firebaseService;
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = firebaseService.getCollection("recipes", Recipe.class);
            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/my-recipes")
    public ResponseEntity<List<Recipe>> getMyRecipes() {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            List<Recipe> recipes = firebaseService.queryDocuments(
                "recipes", "userId", "==", currentUser.getId(), Recipe.class);
            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id) {
        try {
            Recipe recipe = firebaseService.getDocument("recipes", id, Recipe.class);
            if (recipe != null) {
                return ResponseEntity.ok(recipe);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createRecipe(@RequestBody Recipe recipe) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            recipe.setUserId(currentUser.getId());
            recipe.setCreatedAt(System.currentTimeMillis());
            
            String id = firebaseService.createDocument("recipes", recipe);
            Map<String, String> response = new HashMap<>();
            response.put("id", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("title", recipe.getTitle());
            updates.put("description", recipe.getDescription());
            updates.put("ingredients", recipe.getIngredients());
            updates.put("steps", recipe.getSteps());
            
            firebaseService.updateDocument("recipes", id, updates);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
        try {
            firebaseService.deleteDocument("recipes", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
