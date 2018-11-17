package com.example.roby.backingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.adapters.RecipeDetailAdapter;
import com.example.roby.backingapp.model.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.section_recipe_detail)
    TextView recipeStepDescriptionTextView;

    private String recipeStepDescription;
    private List<RecipeStep> mRecipeSteps;

    RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler mClickHandler;

    public RecipeStepViewHolder(@NonNull View itemView, RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler mClickHandler) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mClickHandler = mClickHandler;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = getAdapterPosition();
        RecipeStep recipeStep =  mRecipeSteps.get(adapterPosition);
        mClickHandler.onClick(recipeStep);
    }

    public void setmRecipeSteps(List<RecipeStep> mRecipeSteps) {
        this.mRecipeSteps = mRecipeSteps;
    }

    public TextView getRecipeStepDescriptionTextView() {
        return recipeStepDescriptionTextView;
    }

}
