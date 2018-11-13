package com.example.roby.backingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roby.backingapp.R;

public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView recipeStepDescriptionTextView;
    private String recipeStepDescription;
    public RecipeStepViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeStepDescriptionTextView = itemView.findViewById(R.id.section_recipe_detail);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        openRecipeStepIntent();
    }

    public void setRecipeStepDescription(String recipeStepDescription) {
        this.recipeStepDescription = recipeStepDescription;
    }

    public TextView getRecipeStepDescriptionTextView() {
        return recipeStepDescriptionTextView;
    }

    private void openRecipeStepIntent() {
    }
}
