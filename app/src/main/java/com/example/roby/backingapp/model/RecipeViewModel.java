package com.example.roby.backingapp.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class RecipeViewModel extends AndroidViewModel {
    private JsonLiveData recipeData;
    // Simple in-memory cache. Details omitted for brevity.

    public RecipeViewModel(Application application) {
        super(application);
        recipeData = new JsonLiveData(application);
    }

    public LiveData<List<Recipe>> getRecipeData() {
        return recipeData;
    }
}
