package com.dragos.androidfilepicker.library.objects;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 */
public class ImageItem {
    private String mPath;
    private String mTitle;
    private String mSubtitle;

    public ImageItem() {

    }
    public ImageItem(String path, String title, String subtitle) {
        this.setPath(path);
        this.setTitle(title);
        this.setSubtitle(subtitle);
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }
}
