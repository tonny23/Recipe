package com.example.recipe.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipe.R;


public class RecipeFragment extends Fragment {
    public static final String ARG_RECIPE_ID = "arg_recipe_id";
    public static final String ARG_RECIPE_NAME = "arg_recipe_name";
    public static final String ARG_RECIPE_DESC = "arg_recipe_desc";

    public static RecipeFragment newInstance(int recipeId, String recipeName, String recipeDescription) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);
        args.putSerializable(ARG_RECIPE_NAME, recipeName);
        args.putSerializable(ARG_RECIPE_DESC, recipeDescription);
        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recipeView = inflater.inflate(R.layout.fragment_recipe, container, false);
        TextView nameTextView = (TextView) recipeView.findViewById(R.id.text_view_recipe_name);
        TextView descriptionTextView = (TextView) recipeView.findViewById(R.id.text_view_recipe_description);

        nameTextView.setText(getArguments().getString(ARG_RECIPE_NAME));
        descriptionTextView.setText(getArguments().getString(ARG_RECIPE_DESC));

        return recipeView;
    }

}