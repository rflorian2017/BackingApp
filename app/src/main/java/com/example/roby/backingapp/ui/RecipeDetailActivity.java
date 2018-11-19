package com.example.roby.backingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.fragments.RecipeMasterListFragment;
import com.example.roby.backingapp.fragments.RecipeStepFragment;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeStep;

import butterknife.BindView;

public class RecipeDetailActivity extends AppCompatActivity implements
        RecipeMasterListFragment.OnActionClickListener, RecipeStepFragment.OnNextStepClickListener,
RecipeStepFragment.OnPreviousStepClickListener{

    private static Recipe passedRecipe;
    //store the recipe step for the navigation
    private RecipeStep selectedStep;
    private boolean mTwoPane;

    // store index of selected step
    private int selectedStepIndex;

    @BindView(R.id.rv_recipe_details)
    RecyclerView mRecipeDetailsRecyclerView;

    public static final String RECIPE_PARCEL = "recipe_parcel";
    public static final String RECIPE_STEP_PARCEL = "recipe_step_parcel";
    public static final String RECIPE_STEP_INDEX = "recipe_step_index";
    public static final String RECIPE_BUNDLE = "recipe_bundle";
    public static final String TWO_PANE_BUNDLE = "two_pane";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            passedRecipe = getIntent().getParcelableExtra(RecipeDetailActivity.RECIPE_PARCEL);
        }
        else {
            selectedStepIndex = savedInstanceState.getInt(RECIPE_STEP_INDEX);
            selectedStep = savedInstanceState.getParcelable(RECIPE_STEP_PARCEL);
            passedRecipe = savedInstanceState.getParcelable(RECIPE_PARCEL);
        }

        //set title of activity to recipe name
        this.getSupportActionBar().setTitle(passedRecipe.getRecipeName());

        setContentView(R.layout.activity_recipe_detail);

        //check if we are in two pane layout
        if(findViewById(R.id.tablet_layout_view) != null) {
            mTwoPane = true;
            selectedStep = passedRecipe.getmRecipeSteps().get(selectedStepIndex);

            // to communicate data to fragments we need to store the data in a bundle
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_PARCEL, passedRecipe);
            bundle.putParcelable(RECIPE_STEP_PARCEL, selectedStep);
            bundle.putBoolean(TWO_PANE_BUNDLE, mTwoPane);

            FragmentManager fm = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setArguments(bundle);

            fm.beginTransaction()
                    .add(R.id.step_container, recipeStepFragment)
                    .commit();
        }

        else {
            mTwoPane = false;
        }

    }

    @Override
    public void onActionSelected(RecipeStep selectedStep) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_STEP_PARCEL, selectedStep);
        bundle.putParcelable(RECIPE_PARCEL, passedRecipe);
        if(mTwoPane) {

            bundle.putBoolean(TWO_PANE_BUNDLE, mTwoPane);

            FragmentManager fm = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setArguments(bundle);

            fm.beginTransaction()
                    .add(R.id.step_container, recipeStepFragment)
                    .commit();
        }

        else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            bundle.putBoolean(TWO_PANE_BUNDLE, mTwoPane);
            intent.putExtra(RECIPE_BUNDLE, bundle);
            startActivity(intent);
        }


    }

    /**
     * Save the current state of this activity
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(RECIPE_STEP_PARCEL, selectedStep);
        currentState.putParcelable(RECIPE_PARCEL, passedRecipe);
        currentState.putInt(RECIPE_STEP_INDEX, selectedStepIndex);
    }


    @Override
    public void clickNextStep(int step) {
        stepHandlerFunction(step);
    }

    private void stepHandlerFunction(int step) {
        Bundle bundle = new Bundle();
        selectedStep = passedRecipe.getmRecipeSteps().get(step);
        bundle.putParcelable(RECIPE_STEP_PARCEL, selectedStep);
        bundle.putParcelable(RECIPE_PARCEL, passedRecipe);
        bundle.putBoolean(TWO_PANE_BUNDLE, true);
        FragmentManager fm = getSupportFragmentManager();
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(bundle);

        fm.beginTransaction()
                .add(R.id.step_container, recipeStepFragment)
                .commit();
    }

    public static Recipe getPassedRecipe() {
        return passedRecipe;
    }

    @Override
    public void clickPreviousStep(int step) {
        stepHandlerFunction(step);
    }
}
