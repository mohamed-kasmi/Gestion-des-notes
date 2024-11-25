package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Etudiant;

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

public interface Apietudiant {
    @GET("/etudiant/getalletud")
    Call<List<Etudiant>> getalletud();
    @FormUrlEncoded
    @POST("/etudiant/addetudbyadmin")
    Call<String> addetudbyadmin(@Field("cin") int cin,
                                @Field("classe") String classe);
    @FormUrlEncoded
    @POST("/etudiant/login")
    Call<String> loginetud(@Field("email") String email,
                           @Field("password") String password);
    @PUT("/etudiant/signup/{cin}")
    Call<String> signupetud(@Path("cin") int cin,@Body Etudiant etudiant);
    @DELETE("/etudiant/delete/{cinetud")
    Call<String> deletetud(@Path("cinetud") int cinetud);


}
