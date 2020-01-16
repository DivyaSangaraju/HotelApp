package com.example.sotelapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeLayt;
    ImageView superImageView,log;
    WebView webView;
    ProgressBar superProgressBar;
    Dialog load_dialog;
    String Newurl="https://www.sotel.in/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        swipeLayt= findViewById(R.id.swipelayt);
        webView = findViewById(R.id.webview);
        /*superProgressBar = findViewById(R.id.myProgressBar);
        superImageView = findViewById(R.id.myImageView);*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        swipeLayt.setColorSchemeResources(R.color.colorPrimaryDark);

        WebSettings webSettings = webView.getSettings();

        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        swipeLayt.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());


    }

    private class MyWebViewClient extends WebViewClient {

        Dialog dialog;

        public void onPageStarted (WebView view, String url, Bitmap favicon){ if(!dialog.isShowing()){dialog.show();} }

        public void onPageFinished(WebView view, String url) {
            Newurl = url;
            if(dialog.isShowing()){dialog.dismiss();}
            super.onPageFinished(view, url);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if(dialog.isShowing()){dialog.dismiss();}
            new AlertDialog.Builder(WebViewActivity.this).setMessage("Check Your Connection")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).setCancelable(false).create().show();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenuInflater = getMenuInflater();
        myMenuInflater.inflate(R.menu.super_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.myMenuOne:
                onBackPressed();
                break;

            case R.id.myMenuTwo:
                GoForward();
                break;

            case R.id.myMenuThree:

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Do you want to exit Sotel ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }

                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


        }

        return true;
    }

    private void GoForward() {
        if (webView.canGoForward()) {
            webView.goForward();
        } else {
            Toast.makeText(this, "Can't go further!", Toast.LENGTH_SHORT).show();
        }
    }
}
