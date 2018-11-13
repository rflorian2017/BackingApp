package com.example.roby.backingapp.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.roby.backingapp.utils.JsonUtils;
import com.example.roby.backingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class JsonLiveData extends LiveData<List<Recipe>> {
    private final Context context;
    public JsonLiveData(Context context) {
        this.context = context;
        loadData();
    }
    private void loadData() {
        new AsyncTask<URL,Void,List<Recipe>>() {
            @Override
            protected List<Recipe> doInBackground(URL... urls) {
                URL recipeUrl = NetworkUtils.buildURL();

                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(recipeUrl);
                    List<Recipe> recipes = JsonUtils.getRecipes(jsonWeatherResponse);

                    return recipes;
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }
                return null;
            }
            @Override
            protected void onPostExecute(List<Recipe> data) {
                setValue(data);
            }
        }.execute();
    }
}
