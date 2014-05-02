package com.zabador.flappybrandon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StartActivity extends Activity {

    private final String TAG = "FLappyBrandon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void start(View view) {
        Log.i(TAG, "start was clicked");
        setContentView(new MainGamePanel(this));
    }
}
