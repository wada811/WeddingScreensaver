package com.wada811.wedding.screensaver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment{

    private static final String ARG_FILE_PATH = "ARG_FILE_PATH";
    private String filePath;

    public static ImageFragment newInstance(String filePath){
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_PATH, filePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            filePath = getArguments().getString(ARG_FILE_PATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        imageView.setImageBitmap(BitmapUtils.createBitmapFromFile(filePath, DisplayUtils.getWidth(getActivity()), DisplayUtils.getHeight(getActivity())));
//        Picasso.with(getActivity()).load(filePath).into(imageView);
        return view;
    }



}
