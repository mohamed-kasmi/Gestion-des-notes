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
import retrofit2.http.Query;

public interface Apietudiant {
    @GET("/etudiant/getalletud")
    Call<List<Etudiant>> getalletud();
    @FormUrlEncoded // for sending data in the url request  body
    @POST("/etudiant/addetudbyadmin")
    Call<Void> addetudbyadmin(@Field("cin") int cin,
                                @Field("classe") String classe); // to spesify the key value
    @FormUrlEncoded
    @POST("/etudiant/login")
    Call<Void> loginetud(@Field("email") String email,
                           @Field("password") String password);
    @GET("/etudiant/cin-by-email")
    Call<Integer> getCinEtudByEmail(@Query("email") String email);// for binding the parametre in the query req
    @PUT("/etudiant/signup/{cin}")
    Call<Void> signupetud(@Path("cin") int cin,@Body Etudiant etudiant);
    @DELETE("/etudiant/delete/{cinetud}")
    Call<Void> deletetud(@Path("cinetud") int cinetud);


}
