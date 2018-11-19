package com.example.roby.backingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.fragments.RecipeStepFragment;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeStep;

public class RecipeStepDetailActivity extends AppCompatActivity implements RecipeStepFragment.OnPreviousStepClickListener,
        RecipeStepFragment.OnNextStepClickListener {

    //use a bundle to store data for two pane layout
    private Bundle bundle;

    private RecipeStep currentStep;
    private Recipe selectedRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(null == savedInstanceState) {
            // get all the date from the bundle of the intent
            bundle = getIntent().getBundleExtra(RecipeDetailActivity.RECIPE_BUNDLE);
        }
        else {
            bundle = savedInstanceState.getBundle(RecipeDetailActivity.RECIPE_BUNDLE);
        }

        currentStep = bundle.getParcelable(RecipeDetailActivity.RECIPE_STEP_PARCEL);
        selectedRecipe = bundle.getParcelable(RecipeDetailActivity.RECIPE_PARCEL);

        setContentView(R.layout.activity_step_detail);
        getSupportActionBar().setTitle(selectedRecipe.getRecipeName());
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();

        //set bundle for the passed data
        recipeStepFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.step_container, recipeStepFragment)
                .commit();
    }

    @Override
    public void clickNextStep(int step) {
        changeStepFragment(step);

    }

    private void changeStepFragment(int step) {
        //get current step from list of steps, and change current step
        RecipeStep newStep = selectedRecipe.getmRecipeSteps().get(step);
        currentStep = newStep;

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailActivity.RECIPE_PARCEL, selectedRecipe);
        bundle.putParcelable(RecipeDetailActivity.RECIPE_STEP_PARCEL, newStep);

        //set bundle for the passed data
        recipeStepFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.step_container, recipeStepFragment)
                .commit();
    }

    @Override
    public void clickPreviousStep(int step) {
        changeStepFragment(step);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bundle.putParcelable(RecipeDetailActivity.RECIPE_STEP_PARCEL, currentStep);
        outState.putBundle(RecipeDetailActivity.RECIPE_BUNDLE, bundle);
    }
}
