package com.example.recipe.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.data.RecipeSource;
import com.example.recipe.model.Recipe;


public class RecipeFragment extends Fragment {
    private static final String ARG_RECIPE_ID = "arg_recipe_id";
    public static RecipeFragment newInstance(Integer recipeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);
        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recipeView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ImageView imageView = (ImageView)  recipeView.findViewById(R.id.imageView);
        TextView nameTextView = (TextView) recipeView.findViewById(R.id.text_view_recipe_name);
        TextView descriptionTextView = (TextView) recipeView.findViewById(R.id.text_view_recipe_description);

        Integer recipeId =  getArguments().getInt(ARG_RECIPE_ID);
        RecipeSource recipeSource = RecipeSource.getInstance();

        Recipe recipe = recipeSource.getRecipeById(recipeId);
        imageView.setImageResource(recipe.getImage());
        nameTextView.setText(recipe.getName());
        descriptionTextView.setText(recipe.getDescription());


        return recipeView;
    }
}