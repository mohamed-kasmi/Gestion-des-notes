package com.example.gestion_des_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestion_des_notes.Activityadmin.ActivityProfReq;
import com.example.gestion_des_notes.Activityetud.ActivityNoteEtud;
import com.example.gestion_des_notes.Activityprof.Home_prof;
import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apietudiant;
import com.example.gestion_des_notes.Service.Apiprof;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView T;
    EditText E1, E2;
    Button B;
    RadioButton RaEtud,RaProf;
    Apiprof apiService;
    Apietudiant apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        E1 = findViewById(R.id.Email);
        E2 = findViewById(R.id.Password);
        B = findViewById(R.id.buttonLogin);
        RaEtud=findViewById(R.id.radioButtonEtudiant);
        RaProf=findViewById(R.id.radioButtonEnseignant);
        B.setOnClickListener(v -> {
            String email = E1.getText().toString().trim();
            String password = E2.getText().toString().trim();
            if(email.equals("admin")&&password.equals("admin")) {
                Intent i = new Intent(MainActivity.this, ActivityProfReq.class);
                startActivity(i);
            }else{
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Remplir les champs", Toast.LENGTH_SHORT).show();
                } else {
                    if (RaProf.isChecked()){
                        apiService = Apiapp.getClient().create(Apiprof.class);
                        Call<Void> call = apiService.loginprof(email, password);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(MainActivity.this, Home_prof.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (RaEtud.isChecked()) {
                        apiServices = Apiapp.getClient().create(Apietudiant.class);
                        Call<Void> call = apiServices.loginetud(email,password);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Intent i = new Intent(MainActivity.this, ActivityNoteEtud.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Erreur de connexion.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        T = findViewById(R.id.Text);
        T.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }
}
