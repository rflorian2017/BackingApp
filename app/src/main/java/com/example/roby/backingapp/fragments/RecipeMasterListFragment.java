package com.example.roby.backingapp.fragments;

import android.content.Context;
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
import android.widget.Button;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.adapters.RecipeDetailAdapter;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeStep;
import com.example.roby.backingapp.ui.RecipeDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMasterListFragment extends Fragment implements RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler {
    @BindView(R.id.rv_recipe_details)
    RecyclerView mRecipeDetailsRecyclerView;

    private Recipe passedRecipe;

    private RecipeDetailAdapter mRecipeDetailAdapter;

    //Define an interface that triggers a callback in the host activity
    OnActionClickListener mOnActionCallback;

    @Override
    public void onClick(RecipeStep recipeStep) {
        mOnActionCallback.onActionSelected(recipeStep);
    }

    // action can be ingredient or a recipe step
    // the action is called in the host activity
    public interface OnActionClickListener {
        void onActionSelected(RecipeStep position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //make sure that the host has implemented the callback
        try {
            mOnActionCallback = (OnActionClickListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnActionClcikListener");
        }
    }

    public RecipeMasterListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle !=null) {
            passedRecipe = bundle.getParcelable(RecipeDetailActivity.RECIPE_PARCEL);
        }
        else {
            passedRecipe = this.getActivity().getIntent().getParcelableExtra(RecipeDetailActivity.RECIPE_PARCEL);
        }

        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecipeDetailsRecyclerView.setLayoutManager(layoutManager);

        mRecipeDetailsRecyclerView.setHasFixedSize(true);

        mRecipeDetailAdapter = new RecipeDetailAdapter(passedRecipe.getmRecipeSteps(), passedRecipe, this);
        mRecipeDetailsRecyclerView.setAdapter(mRecipeDetailAdapter);

        return rootView;
    }
}
