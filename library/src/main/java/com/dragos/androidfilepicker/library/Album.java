package com.dragos.androidfilepicker.library;


import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 * modified by BAZTED
 */
class Album {
    private final String mId;
    private final String mName;


    private String subTitle = "";
    private final ArrayList<Image> mImages;

    Album(String mId, String mName) {
        this.mId = mId;
        this.mName = mName;
        this.mImages = new ArrayList<Image>();

    }

    void addImage(Image img) {
        mImages.add(img);
        refreshSubTitle();
    }

    private int getImagesCount() {
        return mImages.size();
    }

    String getThumbPath() {
        if (mImages.size() == 0) {
            return null;
        }
        return mImages.get(0).getPath();
    }

    private void refreshSubTitle() {
        this.subTitle = getImagesCount() + ((getImagesCount() != 1) ? " PHOTOS" : " PHOTO");
    }

    String getSubTitle() {
        return subTitle;
    }

    String getId() {
        return this.mId;
    }

    String getName() {
        return this.mName;
    }

    ArrayList<Image> getImages() {
        return this.mImages;
    }
}
