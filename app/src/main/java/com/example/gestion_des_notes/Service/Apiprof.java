package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Prof;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apiprof {
    @GET("/prof/getallprof")
    Call<List<Prof>> getallprof();
    @POST("/prof/signup")
    Call<String> addprof(@Body Prof prof);
    @POST("/prof/login")
    Call<String> loginprof(@Body Prof prof);
}
