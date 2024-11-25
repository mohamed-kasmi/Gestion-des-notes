package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.Models.Prof;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Apinotes {
    @GET("/note/getallbycinetude")
    Call<List<Notes>> getallbycinetude(@Body Etudiant etudiant);
    @GET("/note/getallbycinprof")
    Call<List<Notes>> getallbycinprof(@Body Prof prof);
    @GET("/note/shearchaddnote")
    Call<List<Notes>> findbycinetud(@Body Prof prof,@Body Etudiant etudiant);
    @POST("/note/addnotes")
    Call<String> addnote(@Body Notes notes);
    @PUT("/note/updateNote/{id}")
    Call<String> updatenote(@Path("id") int id,@Body Notes NOTES);
    @DELETE("/note/delete/{id}")
    Call<String> deletenote(@Path("id") int id);
}
