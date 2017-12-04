package com.example.recipe.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.data.RecipeSource;
import com.example.recipe.fragment.RecipeFragment;
import com.example.recipe.model.Recipe;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private List<Recipe> recipes;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        RecipeSource recipeSource = RecipeSource.getInstance();
        recipes = recipeSource.getRecipes();

        RecipePagerAdapter recipePagerAdapter = new RecipePagerAdapter(getSupportFragmentManager(), recipes);
        mViewPager.setAdapter(recipePagerAdapter);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        for (Recipe recipe:recipes) {
            tabLayout.addTab(tabLayout.newTab().setText(recipe.getName()));
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

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




    private class RecipePagerAdapter extends FragmentStatePagerAdapter {
        private List<Recipe> recipes;
        public RecipePagerAdapter(FragmentManager fm, List<Recipe> recipes) {
            super(fm);
            this.recipes = recipes;
        }
        @Override
        public Fragment getItem(int position) {
            Recipe recipe = recipes.get(position);
            return RecipeFragment.newInstance(recipe.getmId());
        }
        @Override
        public int getCount() {
            return recipes.size();
        }
    }
}
