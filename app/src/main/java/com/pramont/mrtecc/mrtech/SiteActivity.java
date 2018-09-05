package com.pramont.mrtecc.mrtech;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SiteActivity extends AppCompatActivity {

    private final static String URL = "https://reparaciondetelefonos.com.mx/" ;
    private WebView mWebView = null;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        /** WebView embedded no need to add it into any layout**/
        mWebView = new WebView(this);
        setContentView(mWebView);

        /** Enabling java script for web view**/
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        /** Overriding the loading page**/
        mWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        mWebView.loadUrl(URL);
    }

    /**
     * Giving the UX for back button (android) as keyboard back space
     **/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
