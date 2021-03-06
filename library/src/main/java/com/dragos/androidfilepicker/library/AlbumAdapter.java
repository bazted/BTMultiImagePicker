package com.dragos.androidfilepicker.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class AlbumAdapter extends BaseAdapter {

    private final ArrayList<Album> albumList;
    private final Context mContext;

    /**
     * Constructor
     *
     * @param context The current context.
     */
    AlbumAdapter(Context context, ArrayList<Album> items) {
        this.mContext = context;
        this.albumList = items;
    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return albumList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Album getItem(int position) {
        return albumList.get(position);
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
        final Holder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, parent, false);
            viewHolder = new Holder();
            viewHolder.thumb = (ImageView) convertView.findViewById(R.id.grid_view_item_thumb);
            viewHolder.title = (TextView) convertView.findViewById(R.id.grid_view_item_title);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.grid_view_item_subtitle);
            viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        final Album item = getItem(position);

        ImageUtils.displayThumb(mContext, item.getThumbPath(), viewHolder.thumb);

        viewHolder.title.setText(item.getName());
        viewHolder.subtitle.setText(item.getSubTitle());

        viewHolder.check.setVisibility(View.GONE);
        return convertView;
    }

    private class Holder {
        ImageView thumb;
        TextView title;
        TextView subtitle;
        ImageView check;
    }
}
