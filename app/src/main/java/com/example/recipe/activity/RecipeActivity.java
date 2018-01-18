package com.example.recipe.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipe.R;
import com.example.recipe.data.RecipeRepository;
import com.example.recipe.data.local.RecipesContract;
import com.example.recipe.data.model.Recipe;
import com.example.recipe.fragment.DialogRecipeFragment;
import com.example.recipe.fragment.RecipeFragment;

public class RecipeActivity extends AppCompatActivity implements DialogRecipeFragment.RecipeDialogListener {

    private Cursor cursor;
    private RecipePagerAdapter recipePagerAdapter;
    private TabLayout tabLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        getRecipes();
        recipePagerAdapter = new RecipePagerAdapter(getSupportFragmentManager(), cursor);
        mViewPager.setAdapter(recipePagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        updateUI();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate();
            }
        });

        fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipePagerAdapter.getCount() > 0) {
                    deleteRecipe(recipePagerAdapter.getCurrentFragment().getArguments().getInt(RecipeFragment.ARG_RECIPE_ID));
                    updateUI();
                }
            }
        });

    }

    private void getRecipes() {
        RecipeRepository recipeRepository = new RecipeRepository(this);
        // Get all games from the database and add them to the adapter
        cursor = recipeRepository.findAll();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void saveResult(Recipe recipe) {
        // newly created recipes wont have an id
        if (recipe.getmId() == null) {
            saveRecipe(recipe);
        } else {
            updateRecipe(recipe);
        }
        updateUI();
    }

    public void updateRecipe(Recipe recipe) {
        RecipeRepository recipeRepository = new RecipeRepository(this);
        recipeRepository.update(recipe.getmId(), recipe);
    }

    public void deleteRecipe(int id) {
        RecipeRepository recipeRepository = new RecipeRepository(this);
        recipeRepository.delete(id);
    }

    /**
     * Takes the values from the view and attempts to save a game entity within the database.
     * The title and platform input values are required and will result in an error message when
     * empty.
     */
    public void saveRecipe(Recipe recipe) {
        RecipeRepository recipeRepository = new RecipeRepository(this);
        recipeRepository.save(recipe);
    }

    /**
     * Fetches all recipe objects from the database and update the UI in order to show te results.
     */
    private void updateUI() {
        RecipeRepository recipeRepository = new RecipeRepository(this);
        // Get all recipes from the database and add them to the adapter
        cursor = recipeRepository.findAll();
        int currentTab = mViewPager.getCurrentItem();
        recipePagerAdapter.swapCursor(cursor);
        recipePagerAdapter.notifyDataSetChanged();
        tabLayout.removeAllTabs();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            tabLayout.addTab(tabLayout.newTab().setText(cursor.getString(cursor.getColumnIndex(RecipesContract.COLUMN_NAME_NAME))));
        }
        mViewPager.setCurrentItem(currentTab);
    }

    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogRecipeFragment dialogRecipeFragment = DialogRecipeFragment.newInstance("Recipe");
        dialogRecipeFragment.show(fm, "fragment_dialog");
    }

    private void showDialogUpdate() {
        FragmentManager fm = getSupportFragmentManager();
        DialogRecipeFragment dialogRecipeFragment = DialogRecipeFragment.newInstance(
                recipePagerAdapter.getCurrentFragment().getArguments().getInt(RecipeFragment.ARG_RECIPE_ID),
                recipePagerAdapter.getCurrentFragment().getArguments().getString(RecipeFragment.ARG_RECIPE_NAME),
                recipePagerAdapter.getCurrentFragment().getArguments().getString(RecipeFragment.ARG_RECIPE_DESC));
        dialogRecipeFragment.show(fm, "fragment_dialog");
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if(fragment!=null)
                fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                fabAdd.setEnabled(true);
            }
        }
    }*/

    private class RecipePagerAdapter extends FragmentStatePagerAdapter {
        private Cursor cursor;
        private Fragment mCurrentFragment;
        private Recipe recipe;

        public RecipePagerAdapter(FragmentManager fm, Cursor cursor) {
            super(fm);
            this.cursor = cursor;
        }

        @Override
        public Fragment getItem(int position) {
            cursor.moveToPosition(position);
            recipe = new Recipe();
            recipe.setmId(cursor.getInt(cursor.getColumnIndex(RecipesContract.COLUMN_NAME_ID)));
            recipe.setName(cursor.getString(cursor.getColumnIndex(RecipesContract.COLUMN_NAME_NAME)));
            recipe.setDescription(cursor.getString(cursor.getColumnIndex(RecipesContract.COLUMN_NAME_DESCRIPTION)));
            return RecipeFragment.newInstance(recipe.getmId(), recipe.getName(), recipe.getDescription());
        }

        public void swapCursor(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        /**
         * To retrieve the current shown fragment
         * @return the current fragment
         */
        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        /**
         * This will refresh the fragment after calling onNotifyChanged
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
