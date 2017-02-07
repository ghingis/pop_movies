package com.example.ghingis.popmovies.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PopMoviesPreferences {



    public static final String PREFS_NAME = "PopMoviesPreferences";

    public static final String ORDER = "/popular";

    public static final String ORDER_POPULARITY = "/popular";

    public static final String ORDER_TOP_RATED = "/top_rated";

    public static void setOrder(Context context, String order) {
        if (order == ORDER_POPULARITY || order == ORDER_TOP_RATED){
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(ORDER, order);

            // Commit the edits!
            editor.apply();
        }
    }

    public static String getOrder(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(ORDER, ORDER_POPULARITY);
    }

}
