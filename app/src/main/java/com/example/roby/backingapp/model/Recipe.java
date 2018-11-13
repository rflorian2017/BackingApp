package com.example.roby.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
    private String recipeName;
    private String id;
    private List<Ingredient> mIngredients;
    private List<RecipeStep> mRecipeSteps;
    private String servings;
    private String image;

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
//

    public Recipe(String id, String recipeName, List<Ingredient> ingredients, List<RecipeStep> recipeSteps, String servings, String image) {
        this.mIngredients = ingredients;
        this.id = id;
        this.image = image;
        this.mRecipeSteps = recipeSteps;
        this.recipeName = recipeName;
        this.servings = servings;
    }


    protected Recipe(Parcel in) {
        recipeName = in.readString();
        id = in.readString();
        mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        mRecipeSteps = in.createTypedArrayList(RecipeStep.CREATOR);
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(recipeName);
        dest.writeString(id);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mRecipeSteps);
        dest.writeString(servings);
        dest.writeString(image);
    }


    public String getRecipeName() {
        return recipeName;
    }

    public List<RecipeStep> getmRecipeSteps() {
        return mRecipeSteps;
    }
}
