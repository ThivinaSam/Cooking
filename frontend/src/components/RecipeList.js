import React, { useState, useEffect } from 'react';
import { firebaseService } from '../firebase/service';

function RecipeList() {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        setLoading(true);
        const recipeData = await firebaseService.getCollection('recipes');
        setRecipes(recipeData);
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch recipes');
        setLoading(false);
        console.error(err);
      }
    };

    fetchRecipes();
  }, []);

  if (loading) {
    return <div>Loading recipes...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="recipe-list">
      <h2>All Recipes</h2>
      {recipes.length === 0 ? (
        <p>No recipes found. Add your first recipe!</p>
      ) : (
        <ul>
          {recipes.map(recipe => (
            <li key={recipe.id}>
              <h3>{recipe.title}</h3>
              <p>{recipe.description}</p>
              <button>View Details</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default RecipeList;