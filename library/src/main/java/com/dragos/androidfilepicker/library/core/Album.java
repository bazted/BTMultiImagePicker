package com.dragos.androidfilepicker.library.core;


import com.dragos.androidfilepicker.library.objects.ImageItem;

import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 */
public class Album {
    private String mId;
    private String mName;
    private ArrayList<ImageItem> mImages;


    public Album(){
        this.mImages = new ArrayList<ImageItem>();
    }
    public void addImage(ImageItem img) {
        mImages.add(img);
    }

    public int getCount() {
        return mImages.size();
    }

    public String getThumbPath() {
        if (mImages.size() < 0) {
            return null;
        }
        return mImages.get(0).getPath();
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getId() {
        return this.mId;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public ImageItem getImageAt(int index) {
        return mImages.get(index);
    }

    public ArrayList<ImageItem> getImages(){
        return this.mImages;
    }
}
