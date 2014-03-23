package com.dragos.androidfilepicker.library.core;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.dragos.androidfilepicker.library.objects.ImageItem;

import java.util.ArrayList;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 3/23/14.
 */
public class ImageUtils {

    public static ArrayList<Album> getAlbums(Context context) {
        ArrayList<Album> albums = new ArrayList<Album>();


        // which image properties are we querying
        String[] projection =	new String[] {
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
                "", 		// Which rows to return (all rows)
                null, 		// Selection arguments (none)
                "" 			// Ordering
        );

        if (cur.moveToFirst()) {
            int bucketName = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int bucketId   = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int dataIndex  = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            int imageName  = cur.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int imageDate  = cur.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED);


            do {
                /*create a new ImageItem object*/
                ImageItem img = new ImageItem();
                img.setTitle(cur.getString(imageName));
                img.setSubtitle(cur.getString(imageDate));
                img.setPath(cur.getString(dataIndex));

                /*get the bucket id*/
                String bucket_id = cur.getString(bucketId);

                /*check if there is a bucket for this image*/
                boolean ok = false;
                for(int i = 0; i < albums.size(); i++ ) {
                    if(albums.get(i).getId().equals(bucket_id)) {
                        /*found a bucket, add image*/
                        albums.get(i).addImage(img);
                        ok = true;
                        break;
                    }
                }

                if(!ok) { /*this image doesn't have a bucket yet*/
                    Album alb = new Album();
                    alb.setId(bucket_id);
                    alb.setName(cur.getString(bucketName));
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
}
