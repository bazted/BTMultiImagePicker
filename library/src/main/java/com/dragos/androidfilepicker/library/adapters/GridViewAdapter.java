package com.dragos.androidfilepicker.library.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragos.androidfilepicker.library.R;
import com.dragos.androidfilepicker.library.objects.ImageItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 */
public class GridViewAdapter extends BaseAdapter {

    private ArrayList<ImageItem> mItems;
    private boolean mShowTitle;
    private Context mContext;
    private int mSelectedCount;
    private DisplayImageOptions mDisplayOptions;

    /**
     * Constructor
     *
     * @param context The current context.
     */
    public GridViewAdapter(Context context, ArrayList<ImageItem> items, boolean showTitle) {
        this.mContext = context;
        this.mItems = items;
        this.mShowTitle = showTitle;

         mDisplayOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisc(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mItems.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public ImageItem getItem(int position) {
        return mItems.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.grid_view_item, null);

            Holder viewHolder = new Holder();
            viewHolder.thumb = (ImageView) itemView.findViewById(R.id.grid_view_item_thumb);
            viewHolder.title = (TextView) itemView.findViewById(R.id.grid_view_item_title);
            viewHolder.subtitle = (TextView) itemView.findViewById(R.id.grid_view_item_subtitle);
            viewHolder.check = (ImageView) itemView.findViewById(R.id.check);

            itemView.setTag(viewHolder);
        }

        Holder viewHolder = (Holder) itemView.getTag();
        ImageItem item = getItem(position);




        ImageLoader.getInstance().displayImage("file:///" + item.getPath(), viewHolder.thumb, mDisplayOptions);
        if (mShowTitle) {
            viewHolder.title.setText(item.getTitle());
            viewHolder.subtitle.setText(item.getSubtitle());
        } else {
            viewHolder.title.setText("");
            viewHolder.subtitle.setText("");
        }

        viewHolder.check.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);
        return itemView;
    }
    public void setSelectedCount(int selectedCount){
        mSelectedCount = selectedCount;
    }
    public int getSelectedCount(){
        return this.mSelectedCount;
    }
    public boolean showTitle(){
        return this.mShowTitle;
    }
    static class Holder {
        public ImageView thumb;
        public TextView title;
        public TextView subtitle;
        public ImageView check;
    }
}
