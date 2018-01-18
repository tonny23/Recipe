package com.example.recipe.data.local;

import android.provider.BaseColumns;

public interface RecipesContract extends BaseColumns {

    // Labels table name
    public static final String TABLE_NAME = "Recipes";
    // Labels Table Columns names
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
}