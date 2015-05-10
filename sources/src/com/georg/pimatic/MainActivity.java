package com.georg.pimatic;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



@SuppressLint("SetJavaScriptEnabled") public class MainActivity extends Activity {

	Intent intent;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshWebview();
        
        
	   
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	        	refreshWebview();
	            return true;
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openSettings() 
    {
		intent = new Intent(MainActivity.this, ConfigActivity.class);
        startActivity(intent);
    }
	
	public void refreshWebview() 
    {
		String url = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("host_value", "http://www.google.com"); 
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.setWebViewClient(new WebViewClient() {
        	
    	
            @Override
            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        view.getSettings().setDomStorageEnabled(true);
	    File dir = getCacheDir();

	    if (!dir.exists()) {
	      dir.mkdirs();
	    }
	    view.getSettings().setAppCachePath(dir.getPath());
	    view.getSettings().setAllowFileAccess(true);
	    view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
    
}
