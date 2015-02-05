package com.wada811.wedding.screensaver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ScreensaverActivity extends ActionBarActivity{

    private Timer timer;
    private File file;
    private ArrayList<File> fileList;
    private ArrayList<InOutAnimations> animList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screensaver);

        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE //
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_LOW_PROFILE //
                | View.SYSTEM_UI_FLAG_IMMERSIVE //
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //
        );
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(
            new View.OnSystemUiVisibilityChangeListener(){
                @Override
                public void onSystemUiVisibilityChange(int visibility){
//                    if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0){
                        getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE //
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_LOW_PROFILE //
                                | View.SYSTEM_UI_FLAG_IMMERSIVE //
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //
                        );
//                    }
                }
            }
        );

        File appDir = new File(
            Environment.getExternalStorageDirectory(), getString(R.string.app_name)
        );
        File titleDir = new File(appDir, getString(R.string.title_dir));
        File slideDir = new File(appDir, getString(R.string.slide_dir));
        if(!titleDir.exists()){
            titleDir.mkdirs();
        }
        if(!slideDir.exists()){
            slideDir.mkdirs();
        }
        File[] titleFiles = titleDir.listFiles(new ImageFileFilter());
        if(titleFiles.length == 0){
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
            return;
        }
        file = titleFiles[0];
        File[] slideFiles = slideDir.listFiles(new ImageFileFilter());
        if(slideFiles.length == 0){
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
            return;
        }
        fileList = new ArrayList<File>(Arrays.asList(slideFiles));
        animList = new ArrayList<InOutAnimations>();
        animList.add(new InOutAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out));
        animList.add(new InOutAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top));
        animList.add(new InOutAnimations(R.anim.abc_slide_in_left, R.anim.abc_slide_out_right));
        animList.add(new InOutAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom));
        animList.add(new InOutAnimations(R.anim.abc_slide_in_right, R.anim.abc_slide_out_left));
    }

    @Override
    protected void onResume(){
        super.onResume();
        timer = new Timer();
        timer.schedule(new Title(file), 0, TimeUnit.SECONDS.toMillis(10));
        timer.schedule(
            new SlideTask(fileList), TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(10)
        );
    }

    @Override
    protected void onPause(){
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onBackPressed(){
        //        super.onBackPressed();
    }

    private class Title extends TimerTask{

        private File file;

        public Title(File file){
            this.file = file;
        }

        @Override
        public void run(){
            String filePath = file.getAbsolutePath();
            ImageFragment imageFragment = ImageFragment.newInstance(filePath);
            getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.content, imageFragment)
                .commit();
        }
    }

    private class SlideTask extends TimerTask{

        private List<File> fileList;
        private int fileIndex = 0;
        private int animIndex = 0;

        public SlideTask(List<File> fileList){
            this.fileList = fileList;
        }

        @Override
        public void run(){
            if(fileIndex == fileList.size()){
                fileIndex = 0;
            }
            File file = fileList.get(fileIndex);
            fileIndex++;
            if(animIndex == animList.size()){
                animIndex = 0;
            }
            InOutAnimations inOutAnimations = animList.get(animIndex);
            animIndex++;
            String filePath = file.getAbsolutePath();
            ImageFragment imageFragment = ImageFragment.newInstance(filePath);
            getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(inOutAnimations.inAnimation, inOutAnimations.outAnimation)
                .replace(R.id.content, imageFragment)
                .commit();
        }
    }
}
