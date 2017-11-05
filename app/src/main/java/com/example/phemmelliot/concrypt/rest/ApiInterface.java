package com.example.phemmelliot.concrypt.rest;

import com.example.phemmelliot.concrypt.model.Conversion;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiInterface {
    @GET("price")
    Call<Conversion> getPrice(@QueryMap Map<String, String> options);

}
