package com.example.roby.backingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.example.roby.backingapp.R;

public class RecipeIngredientViewHolder extends ViewHolder implements View.OnClickListener{
    private TextView recipeIngredients;

    public RecipeIngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeIngredients = itemView.findViewById(R.id.section_recipe_detail);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        openIngredientsIntent();
    }

    private void openIngredientsIntent() {
    }
}
