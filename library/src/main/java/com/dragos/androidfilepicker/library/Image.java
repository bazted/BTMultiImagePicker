package com.dragos.androidfilepicker.library;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 */
class Image {
    private final String mPath;
    private boolean mSelected;


    Image(String path) {
        this.mPath = path;
    }

    String getPath() {
        return mPath;
    }

    boolean isSelected() {
        return this.mSelected;
    }

    void setSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }
}
