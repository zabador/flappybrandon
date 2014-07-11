package com.zabador.flappybrandon;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by schneis on 7/6/14.
 */
public class SoundHelper {

    MediaPlayer flap, ding, dia, howembarrasing;

    public SoundHelper(Context context) {
        flap = MediaPlayer.create(context, R.raw.flap);
        ding = MediaPlayer.create(context, R.raw.ding);
        dia = MediaPlayer.create(context, R.raw.dia);
        howembarrasing = MediaPlayer.create(context, R.raw.howembarrasing);
        flap.setVolume(1.0f, 1.0f);
        ding.setVolume(1.0f, 1.0f);
        dia.setVolume(1.0f, 1.0f);
        howembarrasing.setVolume(1.0f, 1.0f);
    }

    public void flap() {
        flap.seekTo(0);
        flap.start();
    }

    public void ding() {
        ding.seekTo(0);
        ding.start();
    }

    public void dia() {
        Log.d("sound", "diabetis");
        dia.setVolume(1.0f, 1.0f);
        dia.start();
    }

    public void howembarrasing() {
        stopAll();
        howembarrasing.start();
    }
    public void stopAll() {
        if(flap.isPlaying())
            flap.stop();
        if (ding.isPlaying())
            ding.stop();
        if (dia.isPlaying())
            dia.stop();
        if (howembarrasing.isPlaying())
            howembarrasing.stop();
    }
}
