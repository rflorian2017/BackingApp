package com.example.roby.backingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.adapters.RecipeDetailAdapter;
import com.example.roby.backingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMasterListFragment extends Fragment {
    @BindView(R.id.rv_recipe_details)
    RecyclerView mRecipeDetailsRecyclerView;

    private Recipe passedRecipe;

    private RecipeDetailAdapter mRecipeDetailAdapter;

    public static final String RECIPE_PARCEL = "recipe_parcel";
    public RecipeMasterListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecipeDetailsRecyclerView.setLayoutManager(layoutManager);

        mRecipeDetailsRecyclerView.setHasFixedSize(true);

        Intent intent = getActivity().getIntent();
        passedRecipe = intent.getParcelableExtra(RecipeDetailActivity.RECIPE_PARCEL);
        this.getActivity().setTitle(passedRecipe.getRecipeName());

        mRecipeDetailAdapter = new RecipeDetailAdapter(passedRecipe.getmRecipeSteps());
        mRecipeDetailsRecyclerView.setAdapter(mRecipeDetailAdapter);

        return rootView;
    }
}
