package com.example.nasaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApodDisplay extends AppCompatActivity {

    private TextView textTitle,textExplanation;
    private WebView mWebView;
    private ImageView imageView;
    private int year,month,day;
    private  String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_display);
        imageView=(ImageView) findViewById(R.id.imageView);
        mWebView=(WebView) findViewById(R.id.webView);
        textTitle=(TextView) findViewById(R.id.textTitle);
        textExplanation=(TextView)findViewById(R.id.textExplanation);
        Retrieve();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NasaApi nasaApi=retrofit.create(NasaApi.class);

        Call<Apod> call=nasaApi.getApod("pNFjZHYKoi7zvY6ZPasGra0F2ML8vu2OYrqmyhBx",date);

        call.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                if(!response.isSuccessful())
                {
                    textTitle.setText("Code:"+response.code());
                    textExplanation.setText("");
                    return;
                }

                Apod apod=response.body();
                String date1=apod.getDate();
                String title=apod.getTitle();
                String explanation=apod.getExplanation();
                String media_type=apod.getMedia_type();
                String service_version=apod.getService_version();
                String url=apod.getUrl();
                textTitle.setText("Title:"+title+"\nDate:"+date1);
                textExplanation.setText(explanation);

                if(media_type.equalsIgnoreCase("image"))
                {
                    mWebView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(ApodDisplay.this)
                            .load(url)
                            .into(imageView);
                }
                else
                {
                    imageView.setVisibility(View.INVISIBLE);
                    mWebView.setVisibility(View.VISIBLE);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    mWebView.loadUrl(url);
                    mWebView.setWebChromeClient(new WebChromeClient());

                }




            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                 textTitle.setText(t.getMessage());
                textExplanation.setText("");

            }
        });

    }
    public void Retrieve()
    {
        Intent intent=getIntent();
        date=intent.getStringExtra("date");

    }

}