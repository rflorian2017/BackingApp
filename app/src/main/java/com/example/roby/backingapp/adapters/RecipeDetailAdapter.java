package com.example.roby.backingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.model.Ingredient;
import com.example.roby.backingapp.model.Recipe;
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

    // Store a member for the recipe
    private Recipe currentRecipe;

    // Click handler to allow transition to a detail activity
    final private RecipeDetailAdapterOnClickHandler mClickHandler;

    public interface RecipeDetailAdapterOnClickHandler {
        void onClick(RecipeStep recipeStep);

    }

    public RecipeDetailAdapter(List<RecipeStep> recipeSteps, Recipe currentRecipe, RecipeDetailAdapterOnClickHandler mClickHandler) {
        this.recipeSteps = recipeSteps;
        this.currentRecipe = currentRecipe;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case RECIPE_INGREDIENTS:
                View recipeIngredientsView = inflater.inflate(R.layout.section_ingredient, viewGroup, false);
                viewHolder = new RecipeIngredientViewHolder(recipeIngredientsView);
                break;
            case RECIPE_STEP:
                View recipeStepsView = inflater.inflate(R.layout.section_recipe_step, viewGroup, false);
                viewHolder = new RecipeStepViewHolder(recipeStepsView, mClickHandler);
                ((RecipeStepViewHolder) viewHolder).setmRecipeSteps(recipeSteps);
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
                configureIngredientViewHolder(recipeIngredientViewHolder);
                break;
            case RECIPE_STEP:
                RecipeStepViewHolder recipeStepViewHolder = (RecipeStepViewHolder) viewHolder;
                configureRecipeStepViewHolder(recipeStepViewHolder, position);
                break;
            default:

                break;
        }
    }

    private void configureIngredientViewHolder(RecipeIngredientViewHolder recipeIngredientViewHolder) {
        recipeIngredientViewHolder.getRecipeIngredientsTv().setText(getRecipeIngredientList());
    }

    private String getRecipeIngredientList() {
        String ingredientList = "";
        for (Ingredient ingredient: currentRecipe.getmIngredients())
        {
            ingredientList += ingredient + "\n";
        }
        return ingredientList;
    }

    private void configureRecipeStepViewHolder(RecipeStepViewHolder recipeStepViewHolder, int i) {
        RecipeStep recipeStep = recipeSteps.get(i - 1);
        recipeStepViewHolder.getRecipeStepDescriptionTextView().setText(recipeStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == recipeSteps) return 0;
        return recipeSteps.size() + 1; //+1 for the ingredient list
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if( 0 == position )
            return RECIPE_INGREDIENTS;
        return RECIPE_STEP;
    }
}
