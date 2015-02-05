package com.wada811.wedding.screensaver;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.service.dreams.DreamService;

@TargetApi(VERSION_CODES.JELLY_BEAN_MR1)
public class DayDreamLauncher extends DreamService{

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Intent intent = new Intent(this, ScreensaverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
