package com.wada811.wedding.screensaver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.laevatein.Laevatein;
import com.laevatein.MimeType;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends ActionBarActivity{

    final SettingsActivity self = this;

    private static final int REQ_TITLE_IMAGE = 1;
    private static final int REQ_SLIDE_IMAGE = 2;

    private ImageView titleImage;
    private GridView slideGrid;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView titleBar = (TextView)findViewById(R.id.titleBar);
        titleBar.setOnClickListener(
            new OnClickListener(){
                @Override
                public void onClick(View v){
                    Laevatein.from(self).choose(MimeType.allOf()).forResult(REQ_TITLE_IMAGE);
                }
            }
        );
        titleImage = (ImageView)findViewById(R.id.titleImage);
        TextView slideBar = (TextView)findViewById(R.id.slideBar);
        slideBar.setOnClickListener(
            new OnClickListener(){
                @Override
                public void onClick(View v){
                    Laevatein.from(self)
                        .choose(MimeType.allOf())
                        .count(0, Integer.MAX_VALUE)
                        .forResult(
                            REQ_SLIDE_IMAGE
                        );
                }
            }
        );
        slideGrid = (GridView)findViewById(R.id.slideGrid);
        imageAdapter = new ImageAdapter(this, new ArrayList<Uri>());
        slideGrid.setAdapter(imageAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_TITLE_IMAGE){
            if(resultCode == RESULT_OK){
                List<Uri> selected = Laevatein.obtainResult(data);
                Picasso.with(this).load(selected.get(0)).into(titleImage);
            }
        }else if(requestCode == REQ_SLIDE_IMAGE){
            if(resultCode == RESULT_OK){
                List<Uri> selected = Laevatein.obtainResult(data);
                imageAdapter.addAll(selected);
                imageAdapter.notifyDataSetChanged();
            }
        }
    }
}
