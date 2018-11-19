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
import com.example.roby.backingapp.utils.Utils;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    @BindView
   (R.id.rv_recipes) RecyclerView mRecyclerView;

    private static final int SPAN_COUNT = 2;

    private RecipeAdapter mRecipeAdapter;

    private void showErrorMessage() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) &&
                activeNetwork.isConnected();
        if (isConnected) {

        } else
            Toast.makeText(this, Utils.CHECK_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //set the layout manager to grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, Utils.calculateColumnNumber(this, Utils.RECIPE_CARD_WIDTH));//, GridLayoutManager.VERTICAL, false);
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
