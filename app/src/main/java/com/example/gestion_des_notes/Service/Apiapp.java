package com.example.gestion_des_notes.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiapp {
    public static  String BASE_URL="http://192.168.128.88:8090"; //emulator address 10.0.2.2
    private static Retrofit retrofit;
    public static Retrofit getClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
