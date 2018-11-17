package com.example.roby.backingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.roby.backingapp.adapters.RecipeAdapter;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeViewModel;
import com.example.roby.backingapp.ui.RecipeDetailActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    @BindView
   (R.id.rv_recipes) RecyclerView mRecyclerView;

    private static final String CHECK_INTERNET_CONNECTION = "Please check the internet connection";
    private static final int SPAN_COUNT = 2;
    private static final int RECIPE_CARD_WIDTH = 1000; //recipe card has 1080dp width

    private RecipeAdapter mRecipeAdapter;

    private void showErrorMessage() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) &&
                activeNetwork.isConnected();
        if (isConnected) {

        } else
            Toast.makeText(this, CHECK_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //set the layout manager to grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, calculateColumnNumber());//, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipeData().observe(this,  (recipeData) -> {
            mRecipeAdapter.setRecipeData(recipeData);
            if (null == recipeData) {
                showErrorMessage();
            }
        });
    }

    //compute number of columns based on screen size
    // https://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels
    public int calculateColumnNumber() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        float dpWidth = dm.density * dm.widthPixels;

        int columnNumber = dm.widthPixels / RECIPE_CARD_WIDTH;

        // we want to have at least one column
        return columnNumber < 1 ? 1: columnNumber;
    }

    /**
     * Used to transfer the data between the main activity and the detail activity
     *
     * @param param
     */
    private void launchDetailActivity(Recipe param) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_PARCEL, param);
        startActivity(intent);
    }

    @Override
    public void onClick(Recipe param) {
        launchDetailActivity(param);
    }
}
