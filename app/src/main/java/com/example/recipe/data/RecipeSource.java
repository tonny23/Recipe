package com.example.recipe.data;

import com.example.recipe.R;
import com.example.recipe.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSource {
    private static RecipeSource sRecipeSource;
    private List<Recipe> mRecipes;

    public static RecipeSource getInstance() {
        if (sRecipeSource == null) {
            sRecipeSource = new RecipeSource();
        }
        return sRecipeSource;
    }

    private RecipeSource() {
        mRecipes = new ArrayList<>();
        mRecipes.add(new Recipe(0,"1. Spaghetti", "Some placeholder description.", R.drawable.spaghetti));
        mRecipes.add(new Recipe(1,"2. Hamburger", "Some placeholder description.",R.drawable.hamburger));
        mRecipes.add(new Recipe(2,"3. Meatballs", "Some placeholder description.",R.drawable.meatballs));
        mRecipes.add(new Recipe(3,"4. Pizza", "Some placeholder description.",R.drawable.pizza));
        mRecipes.add(new Recipe(4,"5. Fried Salmon", "Some placeholder description.",R.drawable.salmon));
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public int indexOf(Integer recipeId) {
        for (int i = 0; i < mRecipes.size(); i++) {
            if (mRecipes.get(i).getmId().equals(recipeId)) {
                return i;
            }
        }
        return -1;
    }

    public Recipe getRecipeById(Integer recipeId) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getmId().equals(recipeId)) {
                return recipe;
            }
        }
        return null;
    }
}
