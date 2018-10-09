package com.enggmartservices.enggmart.activities;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFViewActivity extends AppCompatActivity {
    PDFView ca1;
    private String itemPDF = "";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        progressBar = findViewById(R.id.progressbar_pdfviewer);
        progressBar.setVisibility(View.VISIBLE);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
            } else {
                itemPDF = extras.getString("pdf");
            }
        } else {
            itemPDF = (String) savedInstanceState.getSerializable("pdf");
        }


        Log.e("pdf from", itemPDF);
        ca1 = findViewById(R.id.pdf_view);

        new RetrievePDFStream().execute(itemPDF);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {

                URL urlx = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) urlx.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            ca1.fromStream(inputStream).defaultPage(0).load();

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
