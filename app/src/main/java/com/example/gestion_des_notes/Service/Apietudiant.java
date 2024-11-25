package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Etudiant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Apietudiant {
    @GET("/etudiant/getalletud")
    Call<List<Etudiant>> getalletud();
    @POST("/etudiant/addetudbyadmin")
    Call<String> addetudbyadmin(@Body Etudiant etudiant);
    @POST("/etudiant/login")
    Call<String> loginetud(@Body Etudiant etudiant);
    @PUT("/etudiant/signup/{cin}")
    Call<String> signupetud(@Path("cin") int cin,@Body Etudiant etudiant);
    @DELETE("/etudiant/delete/{cinetud")
    Call<String> deletetud(@Path("cinetud") int cinetud);


}
