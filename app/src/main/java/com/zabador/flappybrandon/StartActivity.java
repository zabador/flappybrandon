package com.zabador.flappybrandon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StartActivity extends Activity {

    private final String TAG = "FLappyBrandon";
    private int homePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePage = R.layout.activity_start;
        setContentView(homePage);

    }

    public void start(View view) {
        Log.i(TAG, "start was clicked");
        Intent intent = new Intent(this, BrandonActivity.class);
        startActivity(intent);
    }
}
