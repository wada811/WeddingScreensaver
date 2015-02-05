package com.wada811.wedding.screensaver;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter{

    private final String[] acceptFileExtensions = new String[]{
        "jpg",
        "png",
        "gif",
        "jpeg"
    };

    public ImageFileFilter(){
    }

    public boolean accept(File file){
        for(String extension : acceptFileExtensions){
            if(file.getName().toLowerCase().endsWith(extension)){
                return true;
            }
        }
        return false;
    }

}
