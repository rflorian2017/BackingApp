package com.example.roby.backingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.adapters.RecipeDetailAdapter;
import com.example.roby.backingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipe_details)
    RecyclerView mRecipeDetailsRecyclerView;

    private Recipe passedRecipe;

    private RecipeDetailAdapter mRecipeDetailAdapter;

    public static final String RECIPE_PARCEL = "recipe_parcel";

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe_detail);
//
//        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
//
//        //Use a FragmentManager and a transaction to add the fragment to the screen
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        //Fragment transaction
//        fragmentManager.beginTransaction()
//                .add(R.id.recipe_container, recipeDetailFragment)
//                .commit();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecipeDetailsRecyclerView.setLayoutManager(layoutManager);

        mRecipeDetailsRecyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        passedRecipe = intent.getParcelableExtra(RecipeDetailActivity.RECIPE_PARCEL);
        this.setTitle(passedRecipe.getRecipeName());

        mRecipeDetailAdapter = new RecipeDetailAdapter(passedRecipe.getmRecipeSteps());
        mRecipeDetailsRecyclerView.setAdapter(mRecipeDetailAdapter);
    }
}
