package com.example.gestion_des_notes.Activityprof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.gestion_des_notes.DataBase.ProfDataBase;
import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.Models.Prof;
import com.example.gestion_des_notes.Models.ProfSQL;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apinotes;
import com.example.gestion_des_notes.Service.Apiprof;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_prof extends AppCompatActivity {
Button B;
ImageView Image;
TextView t1,t2,t3;
ProfDataBase db;
Apiprof apiprof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_prof);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_your_icon) {
                    showPopupMenu(findViewById(R.id.toolbar));
                    return true;
                }
                return false;
            }
        });
        SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
        String cin=sp.getString("CIN",null);
        int cinP=Integer.parseInt(cin);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        apiprof= Apiapp.getClient().create(Apiprof.class);
        Call<Prof> call= apiprof.getprof(cinP);
        call.enqueue(new Callback<Prof>() {
            @Override
            public void onResponse(Call<Prof> call, Response<Prof> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Successfully received Prof data
                    Prof prof = response.body();
                    t1.setText("Nom: " + prof.getNom());
                    t2.setText("Prenom: " + prof.getPrenom());
                    t3.setText("Email: " + prof.getEmail());}
            }

            @Override
            public void onFailure(Call<Prof> call, Throwable t) {
                Toast.makeText(Home_prof.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });
        B=findViewById(R.id.Button1);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home_prof.this, add_photo.class);
                startActivity(i);
            }
        });

        db= new ProfDataBase(this);
        Image=findViewById(R.id.immg);
        ProfSQL profSQL=db.getCountryById(cinP);
        if (profSQL != null) {
            Bitmap imgBitmap= BitmapFactory.decodeByteArray(profSQL.getImage(),0,profSQL.getImage().length);
            Image.setImageBitmap(imgBitmap);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void showPopupMenu(View anchor) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        PopupMenu popupMenu = new PopupMenu(this, anchor);

        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    Intent i = new Intent(Home_prof.this, Home_prof.class);
                    startActivity(i);
                    return true;
                }
                else if (item.getItemId() == R.id.Profnote) {
                    Intent i = new Intent(Home_prof.this, ActivityProfnote.class);
                    startActivity(i);
                    return true;
                }else if (item.getItemId() == R.id.addnote) {
                    Intent i = new Intent(Home_prof.this, ActivityAddnote.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.logout) {
                    SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("STAY_CONNECTED", false);
                    editor.apply();
                    vibrator.vibrate(500);
                    Intent i = new Intent(Home_prof.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                    return true;
                }
                return false;
            }

        });

        popupMenu.show();
    }
}
