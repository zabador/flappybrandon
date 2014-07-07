package com.zabador;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by schneis on 7/2/14.
 */
public class App {
    private static App instance;
    private static SharedPreferences settings;
    private static Context context;

    public App(Context context) {
        this.context = context;
    }

    public static void saveScore(int score) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("SCORE", score);
        editor.commit();
    }

    public static int getScore() {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
        int defaultValue = 0;
        int highScore = sharedPref.getInt("SCORE", defaultValue);
        return highScore;
    }
}
