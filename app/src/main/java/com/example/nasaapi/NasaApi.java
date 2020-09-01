package com.example.nasaapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("apod")
    Call<Apod>  getApod(@Query("api_key") String apikey,
                        @Query("date") String date);
}
