package com.example.roby.backingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.model.RecipeStep;
import com.example.roby.backingapp.ui.RecipeIngredientViewHolder;
import com.example.roby.backingapp.ui.RecipeStepViewHolder;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int RECIPE_INGREDIENTS = 0, RECIPE_STEP = 1;

    // Store the context for easy access
    private Context mContext;

    // Store a member variable for the steps
    private List<RecipeStep> recipeSteps;

    public RecipeDetailAdapter(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case RECIPE_INGREDIENTS:
                View recipeIngredientsView = inflater.inflate(R.layout.section_recipe_detail, viewGroup, false);
                viewHolder = new RecipeIngredientViewHolder(recipeIngredientsView);
                break;
            case RECIPE_STEP:
                View recipeStepsView = inflater.inflate(R.layout.section_recipe_detail, viewGroup, false);
                viewHolder = new RecipeStepViewHolder(recipeStepsView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case RECIPE_INGREDIENTS:
                RecipeIngredientViewHolder recipeIngredientViewHolder = (RecipeIngredientViewHolder) viewHolder;
                //configureReviewViewHolder(movieReviewView, position);
                break;
            case RECIPE_STEP:
                RecipeStepViewHolder recipeStepViewHolder = (RecipeStepViewHolder) viewHolder;
                configureRecipeStepViewHolder(recipeStepViewHolder, position);
                break;
            default:

                break;
        }
    }

    private void configureRecipeStepViewHolder(RecipeStepViewHolder recipeStepViewHolder, int i) {
        RecipeStep recipeStep = recipeSteps.get(i);
        recipeStepViewHolder.getRecipeStepDescriptionTextView().setText(recipeStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == recipeSteps) return 0;
        return recipeSteps.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        //if (recipeSteps.get(position - 1) instanceof RecipeStep) {
            return RECIPE_STEP;
       // }
       // return RECIPE_INGREDIENTS;
    }
}
