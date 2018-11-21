package com.example.roby.backingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.example.roby.backingapp.ui.RecipeDetailActivity;
import com.example.roby.backingapp.utils.Utils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        //get recipe name from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PREFERENCE_RECIPE_ID, Context.MODE_PRIVATE);
        String widgetText = sharedPreferences.getString(Utils.APP_WIDGET_RECIPE_NAME_PREFERENCE + appWidgetId, "");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.appwidget_recipe_name, widgetText);

        // Construct an Intent which is pointing this class.
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(Utils.APP_WIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.appwidget_ingredient_list, intent);

        //https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/
        Intent clickIntentTemplate = new Intent(context, RecipeDetailActivity.class);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.appwidget_ingredient_list, clickPendingIntentTemplate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

