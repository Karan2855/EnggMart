package com.enggmartservices.enggmart.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;

public class NewsPaperActivity extends AppCompatActivity {
    private LinearLayout b11, b12, b13, b14, b15, b16, b17, b18, b19;
    private CardView cardNews;
    private WebView webView;
    private ImageView cancle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("Newspaper");
        cancle = findViewById(R.id.web_cancle);
        cardNews = findViewById(R.id.card_news);
        webView = findViewById(R.id.news_web);
        webView.setWebViewClient(new Browser());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        b11 = findViewById(R.id.first);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("https://www.timesofindia.indiatimes.com/");
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
            }
        });

        b12 = findViewById(R.id.second);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("https://www.hindustantimes.com/");
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
            }
        });
        b13 = findViewById(R.id.third);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("https://www.thehindu.com/");
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
            }
        });
        b14 = findViewById(R.id.fourth);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
                webView.loadUrl("https://www.bbc.com/");
            }
        });
        b15 = findViewById(R.id.fifth);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
                webView.loadUrl("https://www.livehindustan.com/");
            }
        });
        b16 = findViewById(R.id.sixth);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
                webView.loadUrl("https://www.jagran.com/");
            }
        });
        b17 = findViewById(R.id.seventh);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
                webView.loadUrl("https://www.bhaskar.com/");
            }
        });
        b18 = findViewById(R.id.eight);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
                webView.loadUrl("https://www.patrika.com/");
            }
        });
        b19 = findViewById(R.id.ninth);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.VISIBLE);
                cardNews.setVisibility(View.GONE);
                webView.loadUrl("https://www.indianexpress.com/");
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancle.setVisibility(View.GONE);
                cardNews.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                webView.loadUrl("");
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class Browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
