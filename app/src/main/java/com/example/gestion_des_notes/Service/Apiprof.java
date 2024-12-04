package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Prof;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apiprof {
    @GET("/prof/getallprof")
    Call<List<Prof>> getallprof();
    @GET("/prof/cin-by-email")
    Call<Integer> getcinprobyfmail(@Query("email") String email);
    @GET("/prof/prof/{cin}")
    Call<Prof> getprof(@Path("cin") int cin);
    @FormUrlEncoded
    @POST("/prof/signup")

    Call<Void> addprof(@Field("cin") int cin,
                       @Field("nom") String nom,
                       @Field("prenom") String prenom,
                       @Field("gener") String gener,
                       @Field("email") String email,
                       @Field("password") String password);

    Call<String> addprof(@Body Prof prof);
    @FormUrlEncoded
    @POST("/prof/login")
    Call<Void> loginprof(@Field("email") String email,
                           @Field("password") String password);
    @DELETE("/profdelete/{cinprof/")
    Call<String> deletprof(@Path("cinprof") int cinprof);

}
