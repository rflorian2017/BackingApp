package com.example.roby.backingapp.utils;

import android.util.Log;


import com.example.roby.backingapp.model.Ingredient;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roby on 2/24/2018.
 */

public class JsonUtils {
    //declare final strings for the fields required from the JSON response
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_STEP_ID = "id";
    private static final String RECIPE_STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String RECIPE_STEP_DESCRIPTION = "description";
    private static final String RECIPE_STEP_VIDEO_URL = "videoURL";
    private static final String RECIPE_STEP_THUMBNAIL_URL = "thumbnailURL";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";


    public static List<Recipe> getRecipes(String json) {
        JSONArray recipeResultsJSON; //declare a JSON object to interpret the string given as a parameter
        try {
            recipeResultsJSON = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        /* example of result :
        {"id":19404,"results":[{"id":"5581bd68c3a3685df70000c6","iso_639_1":"en","iso_3166_1":"US","key":"c25GKl5VNeY","name":"Trailer 1","site":"YouTube","size":720,"type":"Trailer"},
         */

        //read out everything in "one shot" by looping through the JSON array
        List<Recipe> recipeArray = new ArrayList<>();
        try {
            for (int i = 0; i < recipeResultsJSON.length(); i++) {
                JSONObject recipeRow = new JSONObject(recipeResultsJSON.getString(i));
                String recipeId = recipeRow.getString(RECIPE_ID);
                String recipeName = recipeRow.getString(RECIPE_NAME);
                String recipeServings = recipeRow.getString(RECIPE_SERVINGS);
                String recipeImage = recipeRow.getString(RECIPE_IMAGE);
                JSONArray recipeStepsJSON = recipeRow.getJSONArray(RECIPE_STEPS);
                List<RecipeStep> recipeSteps = new ArrayList<>();
                for (int j=0; j< recipeStepsJSON.length(); j++) {
                    JSONObject recipeStepRow = new JSONObject(recipeStepsJSON.getString(j));
                    recipeSteps.add(new RecipeStep(recipeStepRow.getString(RECIPE_STEP_ID),
                            recipeStepRow.getString(RECIPE_STEP_DESCRIPTION),
                            recipeStepRow.getString(RECIPE_STEP_SHORT_DESCRIPTION),
                            recipeStepRow.getString(RECIPE_STEP_VIDEO_URL),
                            recipeStepRow.getString(RECIPE_STEP_THUMBNAIL_URL)));
                }

                JSONArray recipeIngredientsJSON = recipeRow.getJSONArray(RECIPE_INGREDIENTS);
                List<Ingredient> recipeIngredients = new ArrayList<>();
                for (int j=0; j< recipeIngredientsJSON.length(); j++) {
                    JSONObject recipeIngredientRow = new JSONObject(recipeIngredientsJSON.getString(j));
                    recipeIngredients.add(new Ingredient(recipeIngredientRow.getString(INGREDIENT_QUANTITY),
                            recipeIngredientRow.getString(INGREDIENT_MEASURE),
                            recipeIngredientRow.getString(INGREDIENT)));
                }
                recipeArray.add(new Recipe(recipeId, recipeName, recipeIngredients, recipeSteps, recipeServings, recipeImage));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeArray;
    }

}
