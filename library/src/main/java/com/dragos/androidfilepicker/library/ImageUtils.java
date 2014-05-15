package com.dragos.androidfilepicker.library;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 * modified by BAZTED
 */
final class ImageUtils {

    static ArrayList<Album> getAlbums(Context context) {
        ArrayList<Album> albums = new ArrayList<Album>();


        // which image properties are we querying
        String[] projection = new String[]{
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_MODIFIED
        };

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cur = context.getContentResolver().query(images,
                projection, // Which columns to return
                "",        // Which rows to return (all rows)
                null,        // Selection arguments (none)
                ""            // Ordering
        );

        if (cur.moveToFirst()) {
            int bucketName = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int bucketId = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int pathIndex = cur.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                /*create a new ImageItem object*/
                Image img = new Image(cur.getString(pathIndex));

                /*get the bucket id*/
                String bucket_id = cur.getString(bucketId);

                /*check if there is a bucket for this image*/
                boolean ok = false;
                for (Album album : albums) {
                    if (album.getId().equals(bucket_id)) {
                        /*found a bucket, add image*/
                        album.addImage(img);
                        ok = true;
                        break;
                    }
                }

                if (!ok) { /*this image doesn't have a bucket yet*/
                    Album alb = new Album(bucket_id, cur.getString(bucketName));
                    alb.addImage(img);
                    albums.add(alb);
                }
            } while (cur.moveToNext());
        }
        /*close the cursor*/
        cur.close();

        /*return the albums list*/
        return albums;
    }

    static void displayThumb(Context context, String filePath, ImageView into) {
        Picasso.with(context).load("file://" + filePath)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop()
                .into(into);
    }
}
