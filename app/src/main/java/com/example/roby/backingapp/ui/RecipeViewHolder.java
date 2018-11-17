package com.example.roby.backingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.adapters.RecipeAdapter;
import com.example.roby.backingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.recipe_name_tv)
    TextView mRecipeName;

    @BindView(R.id.recipe_number_of_steps)
    TextView mNumberOfRecipeSteps;

    private List<Recipe> mRecipes;
    RecipeAdapter.RecipeAdapterOnClickHandler mClickHandler;

    public RecipeViewHolder(@NonNull View itemView, RecipeAdapter.RecipeAdapterOnClickHandler clickHandler) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        this.mClickHandler = clickHandler;
        itemView.setOnClickListener(this);
    }

    public TextView getmRecipeName() {
        return mRecipeName;
    }

    public TextView getmNumberOfRecipeSteps() {
        return mNumberOfRecipeSteps;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = getAdapterPosition();
        Recipe recipe = mRecipes.get(adapterPosition);
        mClickHandler.onClick(recipe);
    }
}
