package com.zabador.flappybrandon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_start)
public class StartActivity extends Activity {

    private final String TAG = StartActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void start(View view) {
        Log.i(TAG, "start was clicked");
        Intent intent = new Intent(this, BrandonActivity.class);
        startActivity(intent);
    }
}
