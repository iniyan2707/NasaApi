package com.example.nasaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

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

    private VideoView videoView;
    private TextView textView;
    private ImageView imageView;
    private int year,month,day;
    private  String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_display);
        imageView=(ImageView) findViewById(R.id.imageView);
        textView=(TextView) findViewById(R.id.textView2);
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
                    textView.setText("Code:"+response.code());
                    return;
                }

                Apod apod=response.body();
                String date1=apod.getDate();
                String title=apod.getTitle();
                String explanation=apod.getExplanation();
                String media_type=apod.getMedia_type();
                String service_version=apod.getService_version();
                String url=apod.getUrl();
                textView.setText(title+" "+date1);

            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                 textView.setText(t.getMessage());
            }
        });

    }
    public void Retrieve()
    {
        Intent intent=getIntent();
        date=intent.getStringExtra("date");

    }

}