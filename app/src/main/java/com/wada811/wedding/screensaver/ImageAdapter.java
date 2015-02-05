package com.wada811.wedding.screensaver;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Uri>{

    public ImageAdapter(Context context, List<Uri> items){
        super(context, 0, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Log.d(ImageAdapter.class.getSimpleName(), "getView: " + position);
        //        if(convertView == null){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.grid_item_image, parent, false);
        //        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.image);

        Uri item = getItem(position);
        return convertView;
    }
}
