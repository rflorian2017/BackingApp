package com.example.roby.backingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.roby.backingapp.R;

import butterknife.BindView;

public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipe_details)
    RecyclerView mRecipeDetailsRecyclerView;

    public static final String RECIPE_PARCEL = "recipe_parcel";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

    }
}
