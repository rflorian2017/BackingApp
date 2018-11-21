package com.example.roby.backingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.roby.backingapp.model.Ingredient;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.utils.JsonUtils;
import com.example.roby.backingapp.utils.NetworkUtils;
import com.example.roby.backingapp.utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Ingredient> mWidgetItems = new ArrayList<Ingredient>();
    private Context mContext;
    private int mAppWidgetId;
    private int recipeId;
    private List<Recipe> recipes = new ArrayList<Recipe>();

    public RecipeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(Utils.APP_WIDGET,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    // Initialize the data set.
    public void onCreate() {
        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        URL recipeUrl = NetworkUtils.buildURL();

        try {
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(recipeUrl);
            recipes = JsonUtils.getRecipes(jsonWeatherResponse);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        //get recipe id from shared prefs
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Utils.PREFERENCE_RECIPE_ID + mAppWidgetId, Context.MODE_PRIVATE);
        recipeId = sharedPreferences.getInt(Utils.PREFERENCE_RECIPE_ID + mAppWidgetId, -1);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }


    // Given the position (index) of a WidgetItem in the array, use the item's text value in
    // combination with the app widget item XML file to construct a RemoteViews object.
    @Override
    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // Construct a RemoteViews item based on the app widget item XML file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);
        String ingredient = "";
        ingredient += recipes.get(recipeId).getmIngredients().get(position);





        // Return the RemoteViews object.
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
