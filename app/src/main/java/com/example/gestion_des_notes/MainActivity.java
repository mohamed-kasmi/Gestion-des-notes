package com.example.gestion_des_notes;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.gestion_des_notes.Activityprof.ActivityAddnote;
import com.example.gestion_des_notes.Activityprof.ActivityProfnote;
import com.example.gestion_des_notes.Activityprof.Home_prof;
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
    RadioButton RaEtud, RaProf, Checked;
    Apiprof apiService;
    Apietudiant apiServices;
    SharedPreferences mPref;
    String cinEtud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
        boolean isStayConnected = sp.getBoolean("STAY_CONNECTED", false);
        if (isStayConnected) {
            Intent intent = new Intent(MainActivity.this, ActivityNoteEtud.class);
            startActivity(intent);
            return;
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mPref = getSharedPreferences("UserPref", MODE_PRIVATE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        E1 = findViewById(R.id.Email);
        E2 = findViewById(R.id.Password);
        B = findViewById(R.id.buttonLogin);
        RaEtud = findViewById(R.id.radioButtonEtudiant);
        RaProf = findViewById(R.id.radioButtonEnseignant);
        Checked = findViewById(R.id.StayConnected);
        T = findViewById(R.id.Text);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = E1.getText().toString().trim();
                String password = E2.getText().toString().trim();
                if (email.equals("admin") && password.equals("admin")) {
                    Intent i = new Intent(MainActivity.this, ActivityProfReq.class);
                    startActivity(i);
                } else if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Remplir les champs", Toast.LENGTH_SHORT).show();
                } else {
                    if (RaProf.isChecked()) {
                        handleProfLogin(email, password);
                    } else if (RaEtud.isChecked()) {
                        handleEtudLogin(email, password);
                    } else {
                        Toast.makeText(MainActivity.this, "Veuillez sélectionner un rôle", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAddnote.class);
                startActivity(intent);
            }
        });
    }

    private void handleProfLogin(String email, String password) {
        apiService = Apiapp.getClient().create(Apiprof.class);
        Call<Void> callLogin = apiService.loginprof(email, password);
        callLogin.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchProfCIN(email);
                } else {
                    Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProfCIN(String email) {
        Call<Integer> callCIN = apiService.getcinprobyfmail(email);
        callCIN.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String cinProf = String.valueOf(response.body());
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString("CIN", cinProf);
                    editor.apply();
                    if (Checked.isChecked()) {

                        editor.putBoolean("STAY_CONNECTED", true);
                        editor.apply();
                    }
                    Intent intent = new Intent(MainActivity.this, Home_prof.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la récupération du CIN.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleEtudLogin(String email, String password) {
        apiServices = Apiapp.getClient().create(Apietudiant.class);
        Call<Void> callLoginEtud = apiServices.loginetud(email, password);
        callLoginEtud.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchEtudCIN(email);
                } else {
                    Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur de connexion.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEtudCIN(String email) {
        Call<Integer> callCIN = apiServices.getCinEtudByEmail(email);
        callCIN.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cinEtud = String.valueOf(response.body());
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString("CIN_ETUD", cinEtud);
                    editor.apply();
                    if (Checked.isChecked()) {

                        editor.putBoolean("STAY_CONNECTED", true);
                        editor.apply();
                    }
                    Intent intent = new Intent(MainActivity.this, ActivityNoteEtud.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la récupération du CIN étudiant.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
