package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Prof_Req;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Apiprofreq {
    @GET("/profreq/getallprofreq")
    Call<List<Prof_Req>> getProfreq();
    @POST("/profreq/signuprforeq")
    Call<String> addprofreq(@Body Prof_Req profReq);
    @DELETE("/profreq/delete/{cinprof}")
    Call<String> deleteProfreq(@Path("cinprof") int cinprof);

}
