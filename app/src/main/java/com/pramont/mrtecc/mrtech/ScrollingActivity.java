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
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener{

    private static final int REQUEST_PHONE_CALL = 1;
    private Toolbar mToolbar;
    private boolean mAppBarExpanded = false;
    private Menu mMenu;
    private List<Integer> mMenuItemsIds = new ArrayList<>();
    private View mView;
    private AdView mAdViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_scrolling);
        mToolbar = findViewById(R.id.toolbar);
        mView = findViewById(R.id.mainCoordinator);
        mAdViewTop = findViewById(R.id.adViewTop);
        Button siteButton = findViewById(R.id.bt_site);
        CardView cotIpadCV = findViewById(R.id.cv1);

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);

        FloatingActionButton fab = findViewById(R.id.fab);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template")
                .build();

        mAdViewTop.loadAd(adRequest);

        setSupportActionBar(mToolbar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        fab.setOnClickListener(this);
        siteButton.setOnClickListener(this);
        cotIpadCV.setOnClickListener(this);
        getMenuItemsIds();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_site:
                startActivity(new Intent(this,SiteActivity.class));
                break;
            case R.id.fab:
                makeCall(getString(R.string.phone));
                break;
            case R.id.cv1:
                sendAppMsg(view);
                break;
        }
    }

    @Override
    public void onPause() {
        if (mAdViewTop != null) {
            mAdViewTop.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAdViewTop != null) {
            mAdViewTop.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAdViewTop != null) {
            mAdViewTop.destroy();
        }
        super.onDestroy();
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
        mAppBarExpanded = Math.abs(verticalOffset) <= 200;
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
                makeCall(getString(R.string.phone));
                break;
            case R.id.action_map:
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
            Behaviors.showSnackBar(mView, this, getString(R.string.snack_msg_calling),
                    Snackbar.LENGTH_LONG, R.color.greenColor);
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
            //Asking for call permissions.
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ScrollingActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            }
        }
    }

    public void sendAppMsg(View view) {
        Uri uri = Uri.parse("smsto:" + getString(R.string.phone));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_TEXT, "TEST");
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }
}
