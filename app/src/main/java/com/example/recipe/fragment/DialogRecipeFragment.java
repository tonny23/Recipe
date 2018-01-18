package com.example.recipe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.data.model.Recipe;

public class DialogRecipeFragment extends DialogFragment {
    private EditText mName;
    private EditText mDescription;
    private Button mAddRecipe;

    private Recipe recipe;

    public DialogRecipeFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DialogRecipeFragment newInstance(String title) {
        DialogRecipeFragment frag = new DialogRecipeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public static DialogRecipeFragment newInstance(int id, String name, String description) {
        DialogRecipeFragment frag = new DialogRecipeFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("name", name);
        args.putString("description", description);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mName = (EditText) view.findViewById(R.id.etName);
        mDescription = (EditText) view.findViewById(R.id.etDescription);
        mAddRecipe = (Button) view.findViewById(R.id.btnAddRecipe);

        recipe = new Recipe();
        if (getArguments().containsKey("id") && getArguments().containsKey("name") &&
                getArguments().containsKey("description")) {
            recipe.setmId(getArguments().getInt("id"));
            recipe.setName(getArguments().getString("name"));
            recipe.setDescription(getArguments().getString("description"));
            mName.setText(recipe.getName());
            mDescription.setText(recipe.getDescription());
        }

        mAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mName.getText().toString().length() > 0 &&
                        mDescription.getText().toString().length() > 0) {
                    recipe.setName(mName.getText().toString());
                    recipe.setDescription(mDescription.getText().toString());
                    sendBackResult(recipe);
                } else {
                    Snackbar.make(getView(), "Missing name and/or description!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public interface RecipeDialogListener {
        void saveResult(Recipe recipe);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(Recipe recipe) {
        RecipeDialogListener listener = (RecipeDialogListener) getActivity();
        listener.saveResult(recipe);
        dismiss();
    }

}