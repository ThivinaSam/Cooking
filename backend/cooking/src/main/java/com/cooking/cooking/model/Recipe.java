package com.cooking.cooking.model;

import java.util.List;
import java.util.Objects;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String userId;
    private long createdAt;
    
    // Default constructor (required for Firebase deserialization)
    public Recipe() {
    }
    
    // Constructor with all fields except id and createdAt (which are set automatically)
    public Recipe(String title, String description, List<String> ingredients, List<String> steps, String userId) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.userId = userId;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<String> getIngredients() {
        return ingredients;
    }
    
    public List<String> getSteps() {
        return steps;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public long getCreatedAt() {
        return createdAt;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    
    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    
    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return createdAt == recipe.createdAt &&
                Objects.equals(id, recipe.id) &&
                Objects.equals(title, recipe.title) &&
                Objects.equals(description, recipe.description) &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(steps, recipe.steps) &&
                Objects.equals(userId, recipe.userId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, ingredients, steps, userId, createdAt);
    }
    
    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
