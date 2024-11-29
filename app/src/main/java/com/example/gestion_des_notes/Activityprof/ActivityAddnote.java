package com.example.gestion_des_notes.Activityprof;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.Models.Matiere;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apimatiere;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddnote extends AppCompatActivity {
RadioButton rit1,rdsi2,rdsi3,rsem2,rsem3;
Spinner spinner;
Apimatiere apimatiere;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle menu item clicks
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_your_icon) {
                // Show popup menu
                showPopupMenu(findViewById(R.id.toolbar));
                return true;
            }
            return false;
        });
        rit1=findViewById(R.id.Section1);
        rdsi2=findViewById(R.id.Section2);
        spinner = findViewById(R.id.mySpinner);
        apimatiere= Apiapp.getClient().create(Apimatiere.class);

        rit1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String section="";
                if(rit1.isChecked()){
                    section="IT";

                Call<List<String>> call=apimatiere.getmatierebyclasse("DSI2");
                call.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            updateSpinner(response.body());
                        } else {
                            Toast.makeText(ActivityAddnote.this, "Failed to load options", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Toast.makeText(ActivityAddnote.this, "Erreur de connexion.", Toast.LENGTH_SHORT).show();
                    }
                });}
            }
        });

    }
    private void updateSpinner(List<String> options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, // Default spinner item layout
                options
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void showPopupMenu(View anchor) {
        // Create a PopupMenu anchored to the clicked view
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.setGravity(Gravity.END);
        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Handle menu item clicks using if-else
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Show "Hello Home" toast when Home is clicked
                Intent i=new Intent(ActivityAddnote.this, Home_prof.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.addnote) {
                Intent i=new Intent(ActivityAddnote.this, ActivityAddnote.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.logout) {
                Intent i=new Intent(ActivityAddnote.this, MainActivity.class);
                startActivity(i);
            }
            return false;
        });

        // Show the popup menu
        popupMenu.show();
    }
}