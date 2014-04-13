package com.dragos.androidfilepicker.library;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dragos.androidfilepicker.library.adapters.GridViewAdapter;
import com.dragos.androidfilepicker.library.core.Album;
import com.dragos.androidfilepicker.library.core.ImageUtils;
import com.dragos.androidfilepicker.library.objects.ImageItem;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/9/14.
 */
public class PickerFragment extends Fragment {
    private ImagePickerConfig mConfig;
    private GridViewAdapter mGridViewAdapter;
    private GridView mGridView;
    private ArrayList<ImageItem> mImages;
    private boolean mFirstTimeShowHelp = true;
    /**
     * Init a FilePicker with a configuration
     * @param config - FilePicker configuration
     */
    public PickerFragment(ImagePickerConfig config) {
        mConfig = config;
    }

    private View mRootView;



    /**
     * Inits a FilePicker with default config.
     */
    public PickerFragment(){
        mConfig = ImagePickerConfig.createDefault(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initImageLoader(getActivity());
        mRootView = inflater.inflate(R.layout.fragment_picker_grid, container, false);
        initGridViewWithAlbums();
        return mRootView;
    }


    private void initGridViewWithAlbums(){
     mGridView = (GridView) mRootView.findViewById(R.id.gridView);
      final  ArrayList<Album> albums = ImageUtils.getAlbums(getActivity());
        /*create a an ArrayList for the albums*/

       ArrayList<ImageItem> albumsArray = new ArrayList<ImageItem>();

        for(Album album : albums) {
            ImageItem img = new ImageItem();
            img.setTitle(album.getName());
            img.setSubtitle(album.getCount() + ((album.getCount() != 1) ? " PHOTOS" : " PHOTO"));
            img.setPath(album.getThumbPath());
            albumsArray.add(img);
        }
        mGridViewAdapter = new GridViewAdapter(getActivity(), albumsArray, true);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mFirstTimeShowHelp) {
                    Toast.makeText(getActivity(), "Use long tap to enter selection mode.", Toast.LENGTH_LONG).show();
                    mFirstTimeShowHelp = !mFirstTimeShowHelp;
                }

               mImages = albums.get(position).getImages();
                mGridViewAdapter = new GridViewAdapter(getActivity(), mImages, false);

                mGridViewAdapter.notifyDataSetChanged();
                mGridView.invalidate();
                mGridView.setAdapter(mGridViewAdapter);
                mGridView.setOnItemClickListener(null);

                mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
                mGridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        ImageItem imgItem = mImages.get(position);
                        if(imgItem.isSelected()) {
                            mGridViewAdapter.setSelectedCount(mGridViewAdapter.getSelectedCount() - 1);
                        } else {
                            mGridViewAdapter.setSelectedCount(mGridViewAdapter.getSelectedCount() + 1);
                        }
                        imgItem.setSelected(!imgItem.isSelected());
                        mGridViewAdapter.notifyDataSetChanged();
                        int count = mGridViewAdapter.getSelectedCount();
                        if(count == 0) {
                            mode.setTitle("Tap to select");
                        } else {
                            mode.setTitle(count + ((count == 1) ? " image " : " images " )  + "selected");
                        }
                        Log.w("position", "pos: " + position);
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        if(!mGridViewAdapter.showTitle()) {
                            mode.getMenuInflater().inflate(R.menu.picker_context_menu, menu);
                        } else {
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return true;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                       int id = item.getItemId();
                        if (id == R.id.sendSelection) {
                            sendSelection();
                            mode.finish();
                        } else {
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        for(ImageItem imgItem : mImages) {
                            imgItem.setSelected(false);
                        }
                        mGridViewAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        mGridViewAdapter.notifyDataSetChanged();
        mGridView.invalidate();
        mGridView.setAdapter(mGridViewAdapter);

    }
    private void sendSelection(){
        ArrayList<String> imagePaths = new ArrayList<String>();
        for(ImageItem imgItem : mImages) {
            if(imgItem.isSelected()) {
                imagePaths.add(imgItem.getPath());
            }
        }
        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra(Constants.IMAGE_PICKER_PATHS_EXTRA_KEY,  imagePaths);
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.

        ImageLoader.getInstance().init(config);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("DBG", "fragment");
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mGridViewAdapter.showTitle()) {
                    Intent returnIntent = new Intent();
                    getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
                    getActivity().finish();

                } else {
                    initGridViewWithAlbums();
                }



                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

}
