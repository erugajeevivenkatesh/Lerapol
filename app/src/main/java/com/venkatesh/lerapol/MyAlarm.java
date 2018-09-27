package com.venkatesh.lerapol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer=MediaPlayer.create(context,Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
        Log.d("ALARM","Alaram is started");

    }
}
