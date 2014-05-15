package com.dragos.androidfilepicker.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.dragos.androidfilepicker.library.core.Constants;

import java.util.ArrayList;

public class ImagePickerActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private View progressBar;
    private View emptyText;

    private ImageAdapter imageAdapter;
    private AlbumAdapter albumAdapter;
    private ActionBar supportActionBar;

    private boolean insideAlbum = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_grid);
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.choose_album);
        supportActionBar.setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.pb);
        emptyText = findViewById(R.id.empty_tv);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setOnItemClickListener(this);
        startLoader();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int itemId = item.getItemId();
        if (itemId == R.id.action_accept) {
            if (imageAdapter != null && imageAdapter.getSelectedCount() > 0) {
                sendPathsInResult(imageAdapter.getSelectedPaths());
            }
            return true;
        }
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (insideAlbum && imageAdapter.getSelectedCount() > 0) {
            getMenuInflater().inflate(R.menu.accept, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void startLoader() {
        new AlbumsLoader().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (albumAdapter != null && !insideAlbum) {
            setImageAdapter(albumAdapter.getItem(position).getImages());
        } else {
            if (imageAdapter != null) {
                imageAdapter.setSelected(position);
                supportInvalidateOptionsMenu();
                final int selectedCount = imageAdapter.getSelectedCount();
                if (selectedCount > 0) {
                    supportActionBar.setSubtitle(String.format(getString(R.string.count_format), selectedCount));
                } else {
                    supportActionBar.setSubtitle(null);
                }
            }
        }
    }

    private void setImageAdapter(final ArrayList<Image> albumImages) {
        supportActionBar.setTitle(R.string.choose_images);
        imageAdapter = new ImageAdapter(this, albumImages);
        mGridView.setAdapter(imageAdapter);
        insideAlbum = true;
    }

    private void revealImageAdapter() {
        if (imageAdapter != null) {
            imageAdapter.clearSelected();
        }
        insideAlbum = false;
        imageAdapter = null;
        supportActionBar.setTitle(R.string.choose_album);
        supportActionBar.setSubtitle(null);
        supportInvalidateOptionsMenu();
    }

    private void setAlbumAdapter(final ArrayList<Album> albums) {
        if (albums != null && albums.size() > 0) {
            albumAdapter = new AlbumAdapter(this, albums);
            mGridView.setAdapter(albumAdapter);
            progressBar.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            revealImageAdapter();
        } else {
            progressBar.setVisibility(View.GONE);
            mGridView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    private void setAlbumAdapter(final AlbumAdapter albumAdapter) {
        mGridView.setAdapter(albumAdapter);
        revealImageAdapter();
    }


    private void sendPathsInResult(final ArrayList<String> paths) {
        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra(Constants.IMAGE_PICKER_PATHS_EXTRA_KEY, paths);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!insideAlbum) {
            super.onBackPressed();
        } else {
            revealImageAdapter();
            if (albumAdapter == null) {
                startLoader();
            } else {
                setAlbumAdapter(albumAdapter);
            }
        }
    }

    private class AlbumsLoader extends AsyncTask<Void, Void, ArrayList<Album>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Album> doInBackground(Void... params) {
            if (!isCancelled()) {

                return ImageUtils.getAlbums(ImagePickerActivity.this);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Album> albums) {
            super.onPostExecute(albums);
            if (albums != null) {
                if (albums.size() > 0) {
                    setAlbumAdapter(albums);

                }
            }
        }

    }


}
