package com.zabador.flappybrandon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zabador.App;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_start)
public class StartActivity extends Activity {

    @ViewById(R.id.score)
    TextView lblscore;

    private final String TAG = StartActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews(){
        new App(this);
        lblscore.setText(getString(R.string.score, App.getScore()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        lblscore.setText(getString(R.string.score, App.getScore()));
    }

    public void start(View view) {
        Log.i(TAG, "start was clicked");
        Intent intent = new Intent(this, BrandonActivity.class);
        startActivity(intent);
    }
}
