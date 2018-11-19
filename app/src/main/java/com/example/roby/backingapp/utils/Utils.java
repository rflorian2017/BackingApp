package com.example.roby.backingapp.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utils {

    public static final int RECIPE_CARD_WIDTH = 1000; //recipe card has 1080dp width
    public static final String CHECK_INTERNET_CONNECTION = "Please check the internet connection";
    public static final String PREF_RECIPE_NAME = "RECIPE_NAME";

    //compute number of columns based on screen size
    // https://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels
    public static int calculateColumnNumber(Activity activity, int width) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        float dpWidth = dm.density * dm.widthPixels;

        int columnNumber = dm.widthPixels / width;

        // we want to have at least one column
        return columnNumber < 1 ? 1: columnNumber;
    }
}
