package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Activityprof.Prof;
import com.example.gestion_des_notes.Models.Prof_Req;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Apiprofreq {
    @GET("/profreq/getallprofreq")
    Call<List<Prof_Req>> getProfreq();
    @FormUrlEncoded
    @POST("/profreq/signuprforeq")
    Call<Void> addprofreq( @Field("cin") int cin,
                           @Field("nom") String nom,
                           @Field("prenom") String prenom,
                           @Field("gener") String gener,
                           @Field("email") String email,
                           @Field("password") String password);
    @DELETE("/profreq/delete/{cinprof}")
    Call<Void> deleteProfreq(@Path("cinprof") int cinprof);

}
