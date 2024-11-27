package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Matiere;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apimatiere {
    @GET("/matiere/getallmat")
    Call<List<Matiere>> getallmatiere();
    @GET("/matiere/matierebyclasse")
    Call<List<Matiere>> getmatierebyclasse(@Body Matiere matiere);
    @FormUrlEncoded
    @POST("/matiere/addmatiere")
    Call<Void> addmatiere(@Field("matiere") String matiere,
                          @Field("classe") String session,
                          @Field("cof") Double cof);
    @PUT("/matiere/updatematiere/{idmatiere}")
    Call<Void> updatematiere(@Path("idmatiere") int idmatiere,@Body Matiere matiere);
    @DELETE("/matiere/delete/{idmatiere}")
    Call<Void> deletematiere(@Path("idmatiere") int idmatiere);
}
