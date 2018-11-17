package com.example.roby.backingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {
//    @BindView(R.id.textViewRecipeName)
//    TextView recipeName;

//    @BindView(R.id.rv_recipe_detail)
//    RecyclerView mRecipeDetailsRecyclerView;

    private Recipe passedRecipe;

    public RecipeDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
//
//        ButterKnife.bind(this, view);
//
//        Intent intent = getActivity().getIntent();
//        passedRecipe = intent.getParcelableExtra(RecipeDetailActivity.RECIPE_PARCEL);
//        getActivity().setTitle(passedRecipe.getRecipeName());
//
//        //recipeName.setText(passedRecipe.getRecipeName());
        return null;
    }
}
