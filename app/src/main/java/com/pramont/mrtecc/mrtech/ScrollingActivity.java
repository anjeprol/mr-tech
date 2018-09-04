package com.pramont.mrtecc.mrtech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int REQUEST_PHONE_CALL = 1;
    private boolean mAppBarExpanded = false;
    private Menu mMenu;
    private List<Integer> mMenuItemsIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.snack_msg_calling, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                makeCall(getString(R.string.phone));
            }
        });

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        getMenuItemsIds();
    }

    /**
     * Called to get all the menu item ids to be displayed.
     */
    private void getMenuItemsIds() {
        mMenuItemsIds.add(R.id.action_call);
        mMenuItemsIds.add(R.id.action_about);
        mMenuItemsIds.add(R.id.action_map);

    }

    /**
     * Called when the {@link AppBarLayout}'s layout offset has been changed. This allows
     * child views to implement custom behavior based on the offset (for instance pinning a
     * view at a certain y value).
     *
     * @param appBarLayout   the {@link AppBarLayout} which offset has changed
     * @param verticalOffset the vertical offset for the parent {@link AppBarLayout}, in px
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //  Vertical offset == 0 indicates appBar is fully  expanded.
        invalidateOptionsMenu();
        if (Math.abs(verticalOffset) > 200)
        {
            mAppBarExpanded = false;
        }
        else
        {
            mAppBarExpanded = true;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        mMenu = menu;
        if (mAppBarExpanded)
        {
            setOptionsMenuVisible(mMenuItemsIds, false);
        }
        else
        {
            setOptionsMenuVisible(mMenuItemsIds, true);
        }
        return true;
    }

    private void setOptionsMenuVisible(List<Integer> optionsMenu, boolean visible) {
        for (Integer id : optionsMenu)
        {
            MenuItem item = mMenu.findItem(id);
            item.setVisible(visible);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_about:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_call:
                Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
                makeCall(getString(R.string.phone));
                break;
            case R.id.action_map:
                Toast.makeText(this, "map", Toast.LENGTH_SHORT).show();
                getMaps(getString(R.string.location_url));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Gets the location and send the directions using google maps.
     *
     * @param uri String uri from maps.
     */
    private void getMaps(String uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }


    /**
     * To make a call to proper cinema.
     *
     * @param phone String phone number.
     */
    @SuppressLint("MissingPermission")
    private void makeCall(String phone) {
        final String TAG = "Permissions";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        try
        {
            startActivity(intent);
        }
        catch (Exception ex)
        {
            requestPermissions();
            Log.d(TAG, "No permissions granted \n" + ex.getMessage());
        }
    }

    /**
     * To request all the permission once the application starts.
     */
    private void requestPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            /**
             * Asking for call permission.
             */
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ScrollingActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            }
        }
    }

}
