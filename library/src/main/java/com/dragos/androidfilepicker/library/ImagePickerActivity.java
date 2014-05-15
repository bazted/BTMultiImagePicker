package com.dragos.androidfilepicker.library;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Dragos Raducanu (raducanu.dragos@gmail.com) on 4/13/14.
 */


public class ImagePickerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PickerFragment pickerFragment = new PickerFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, pickerFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("DBG", "activity");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
