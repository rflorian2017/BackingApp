package com.example.roby.backingapp.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.RecipeWidget;
import com.example.roby.backingapp.adapters.RecipeAdapter;
import com.example.roby.backingapp.model.Ingredient;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeViewModel;
import com.example.roby.backingapp.utils.Utils;

//https://medium.com/android-bits/android-widgets-ad3d166458d3

public class RecipeWidgetConfigureActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    RecyclerView mRecyclerViewRecipeList;

    RecipeAdapter mRecipeAdapter;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_CANCELED);
        // activity stuffs
        setContentView(R.layout.activity_widget_configure);

        mRecyclerViewRecipeList = findViewById(R.id.widget_recipe_list_rv);
        //set the layout manager to grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, Utils.calculateColumnNumber(this, Utils.RECIPE_CARD_WIDTH));//, GridLayoutManager.VERTICAL, false);
        mRecyclerViewRecipeList.setLayoutManager(layoutManager);

        mRecyclerViewRecipeList.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerViewRecipeList.setAdapter(mRecipeAdapter);

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipeData().observe(this,  (recipeData) -> {
            mRecipeAdapter.setRecipeData(recipeData);
            if (null == recipeData) {
                showErrorMessage();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }

    @Override
    public void onClick(Recipe param) {
        final Context context = RecipeWidgetConfigureActivity.this;

        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(Utils.PREFERENCE_RECIPE_ID, 0).edit();
        sharedPreferences.putInt(Utils.PREFERENCE_RECIPE_ID + mAppWidgetId, Integer.parseInt(param.getId()));
        sharedPreferences.putString(Utils.APP_WIDGET_RECIPE_NAME_PREFERENCE + mAppWidgetId, param.getRecipeName());
        sharedPreferences.apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RecipeWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        Intent resultValue = new Intent();
        // Set the results as expected from a 'configure activity'.
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

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
}
