package com.example.gestion_des_notes.Service;

import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.Models.Prof;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apinotes {
    @GET("/note/getallbycinetude")
    Call<List<Notes>> getallbycinetude(@Query("cinetud") int cinetud);
    @GET("/note/getallbycinprof")
    Call<List<Notes>> getallbycinprof(@Query("cinprof") int cinprof);

    @GET("/note/searchmatiereedtud")
    Call<List<Notes>> getallbycinetudandmatiere(@Query("cinetud") int cinetud,@Query("matiere") String matiere);
    @GET("/note/shearchaddnote")
    Call<List<Notes>> findbycinetud(@Query("cinetud") int cinetud,@Query("cinprof") int cinprof);
    @POST("/note/addnotes")
    Call<String> addnote(@Body Notes notes);
    @PUT("/note/updateNote/{id}")
    Call<Void> updatenote(@Path("id") int id,@Body Notes NOTES);
    @DELETE("/note/delete/{id}")
    Call<Void> deletenote(@Path("id") int id);
}
