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

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private List<Recipe> mRecipes;
    RecipeAdapter.RecipeAdapterOnClickHandler mClickHandler;

    public RecipeViewHolder(@NonNull View itemView, RecipeAdapter.RecipeAdapterOnClickHandler clickHandler) {
        super(itemView);
        //mRecipeImage = itemView.findViewById(R.id.recipe_iv);
        mRecipeName = itemView.findViewById(R.id.recipe_name_tv);
        this.mClickHandler = clickHandler;
        itemView.setOnClickListener(this);
    }

    public ImageView getmRecipeImage() {
        return mRecipeImage;
    }

    public TextView getmRecipeName() {
        return mRecipeName;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = getAdapterPosition();
        Recipe movie = mRecipes.get(adapterPosition);
        mClickHandler.onClick(movie);
    }
}
