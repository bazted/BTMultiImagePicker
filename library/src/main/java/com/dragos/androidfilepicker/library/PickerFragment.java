package com.dragos.androidfilepicker.library;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/9/14.
 */
public class PickerFragment extends Fragment {
    private FilePickerConfig mConfig;

    /**
     * Init a FilePicker with a configuration
     * @param config - FilePicker configuration
     */
    public PickerFragment(FilePickerConfig config) {
        mConfig = config;
    }

    /**
     * Inits a FilePicker with default config.
     */
    public PickerFragment(){
        mConfig = FilePickerConfig.createDefault(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_picker, container, false);
        TextView dbg = (TextView) rootView.findViewById(R.id.debug);
        dbg.setText(mConfig.getDisplayMode());
        return rootView;
    }

    /**
     * Lists files from parent directory.
     * @param parentDirectory - list files from subdirectories.
     * @return an ArrayList containing all files.
     */
    private ArrayList<File> listFiles(String parentDirectory, Boolean recursive){
        ArrayList<File> files = new ArrayList<File>();

        return files;
    }

    /**
     * Lists files from the root storage directory.
     * @param recursive - list files from subdirectories.
     * @return an ArrayList containing all files.
     */
    private ArrayList<File> listFiles(Boolean recursive) {
        return listFiles(Environment.getExternalStorageDirectory().getAbsolutePath(), recursive);
    }

    private void listImages(){

    }
}
