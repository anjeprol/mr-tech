package com.pramont.mrtecc.mrtech;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private Menu collapsedMenu;
    private boolean appBarExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(this);
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
        if (Math.abs(verticalOffset) > 200)
        {
            appBarExpanded = false;
            invalidateOptionsMenu();
        }
        else
        {
            appBarExpanded = true;
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        MenuItem item = menu.findItem(R.id.action_call);
        MenuItem item2 = menu.findItem(R.id.action_about);
        if (appBarExpanded)
        {
            item.setVisible(false);
            item2.setVisible(false);
        } else {
            item.setVisible(true);
            item2.setVisible(true);
        }

        collapsedMenu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
            Log.d(ScrollingActivity.class.getName(), "about");
            return true;
        }
        else if (id == R.id.action_call)
        {
            Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
            Log.d(ScrollingActivity.class.getName(), "call");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
