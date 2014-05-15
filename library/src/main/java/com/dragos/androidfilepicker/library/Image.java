package com.dragos.androidfilepicker.library;

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
