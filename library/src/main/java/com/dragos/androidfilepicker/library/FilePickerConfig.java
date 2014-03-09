package com.dragos.androidfilepicker.library;

import android.content.Context;
import android.os.Environment;

import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/9/14.
 */
public final class FilePickerConfig {

    final private ArrayList<String> mExcludeFolders;
    final private ArrayList<String> mFileTypes;
    final private String mParentPath;
    final private String mDisplayMode;
    final private boolean mPreserveFolderStructure;
    final private boolean mMultiSelect;
    final private boolean mAllowMultiFileTypes;

    private FilePickerConfig(final Builder builder){
        this.mExcludeFolders = builder.mExcludeFolders;
        this.mFileTypes = builder.mFileTypes;
        this.mParentPath = builder.mParentPath;
        this.mDisplayMode = builder.mDisplayMode;
        this.mPreserveFolderStructure = builder.mPreserveFolderStructure;
        this.mMultiSelect = builder.mMultiSelect;
        this.mAllowMultiFileTypes = builder.mAllowMultiFileTypes;
    }
    public static FilePickerConfig createDefault(Context context) {
        return new Builder(context).build();
    }

    public ArrayList<String> getExcludedFolders(){
        return this.mExcludeFolders;
    }
    public ArrayList<String> getFileTypes(){
        return this.mFileTypes;
    }
    public String getParentPath(){
        return this.mParentPath;
    }
    public String getDisplayMode(){
        return this.mDisplayMode;
    }
    public boolean shouldPreserverFolderStructure(){
        return this.mPreserveFolderStructure;
    }
    public boolean allowMultiSelect(){
        return this.mMultiSelect;
    }
    public boolean allowMultifileTypes(){
        return this.mAllowMultiFileTypes;
    }


    public static class Builder {

        private Context context;

        private ArrayList<String> mExcludeFolders;
        private ArrayList<String> mFileTypes;
        private String mParentPath;
        private String mDisplayMode;
        private boolean mPreserveFolderStructure = false;
        private boolean mMultiSelect = true;
        private boolean mAllowMultiFileTypes = true;

        public Builder(Context context){
            this.context = context;
        }

        public Builder excludeFolders(ArrayList<String> foldersToExclude) {
            this.mExcludeFolders = foldersToExclude;
            return this;
        }
        public Builder allowFileTypes(ArrayList<String> fileTypes) {
            this.mFileTypes = fileTypes;
            return this;
        }
        public Builder parentPath(String parentPath) {
            this.mParentPath = parentPath;
            return this;
        }
        public Builder displayMode(String displayMode) {
            this.mDisplayMode = displayMode;
            return this;
        }
        public Builder preserveFolderStructure(boolean preserve) {
            this.mPreserveFolderStructure = preserve;
            return this;
        }
        public Builder multiSelect(boolean multiSelect) {
            this.mMultiSelect = multiSelect;
            return this;
        }
        public Builder allowMultiFileTypes(boolean allowMultiFileTypes) {
            this.mAllowMultiFileTypes = allowMultiFileTypes;
            return this;
        }
        public FilePickerConfig build() {
            initEmptyFieldsWithDefaultValues();
            return new FilePickerConfig(this);
        }
        private void initEmptyFieldsWithDefaultValues(){
            if(this.mParentPath == null) {
                this.mParentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            if(this.mDisplayMode == null) {
                this.mDisplayMode = Constants.DISPLAY_MODE_GRID;
            }
        }
    }

}
