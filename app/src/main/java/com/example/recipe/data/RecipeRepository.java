package com.example.recipe.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recipe.data.local.DBHelper;
import com.example.recipe.data.local.RecipesContract;
import com.example.recipe.data.model.Recipe;


public class RecipeRepository {

    private SQLiteDatabase database;
    private final DBHelper dbHelper;
    private final String[] RECIPES_ALL_COLUMNS = {
            RecipesContract.COLUMN_NAME_ID,
            RecipesContract.COLUMN_NAME_NAME,
            RecipesContract.COLUMN_NAME_DESCRIPTION };

    public RecipeRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Save an object within the database.
     *
     * @param recipe the object to be saved.
     */
    public void save(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipesContract.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipesContract.COLUMN_NAME_DESCRIPTION, recipe.getDescription());
        // Inserting Row
        db.insert(RecipesContract.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Update a single entity within the database.
     *
     * @param id   the id of the entity to be updated.
     * @param recipe holds the new values which will overwrite the old values.
     */
    public void update(int id, Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipesContract.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipesContract.COLUMN_NAME_DESCRIPTION, recipe.getDescription());

        db.update(RecipesContract.TABLE_NAME, values, RecipesContract.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
    }

    /**
     * Finds all recipe objects.
     *
     * @return a cursor holding the recipe objects.
     */
    public Cursor findAll() {
        // If we have not yet opened the database, open it
        if (database == null) {
            database = dbHelper.getReadableDatabase();
        }

        return database.query(RecipesContract.TABLE_NAME, RECIPES_ALL_COLUMNS, null, null, null, null, null);
    }

    /**
     * Delete a single entity from the database.
     *
     * @param id the id of the entity to be deleted.
     */
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(RecipesContract.TABLE_NAME, RecipesContract.COLUMN_NAME_ID + " =?",
                new String[]{Integer.toString(id)});
        db.close();
    }
}
