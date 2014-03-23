package com.dragos.androidfilepicker.library;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.ImageView;

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
    GridViewAdapter mGridViewAdapter;
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
        Log.e("DBG", "initing!");
      final GridView gridView = (GridView) mRootView.findViewById(R.id.gridView);
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

                ArrayList<ImageItem> images = albums.get(position).getImages();
                mGridViewAdapter = new GridViewAdapter(getActivity(), images, false);

                mGridViewAdapter.notifyDataSetChanged();
                gridView.invalidate();
                gridView.setAdapter(mGridViewAdapter);
                gridView.setOnItemClickListener(null);

                gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
                gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        ImageView img = (ImageView)gridView.getChildAt(position).findViewById(R.id.check);
                        img.setVisibility(checked ? View.VISIBLE : View.INVISIBLE);
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.picker_context_menu, menu);
                        mode.setTitle("Select items");
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return true;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }
                });

            }
        });
        mGridViewAdapter.notifyDataSetChanged();
        gridView.invalidate();
        gridView.setAdapter(mGridViewAdapter);

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
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                initGridViewWithAlbums();
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

}
