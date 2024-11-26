package com.example.gestion_des_notes.Activityadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Adapters.EtudAdapter;
import com.example.gestion_des_notes.Adapters.ProfreqAdapter;
import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Prof_Req;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apietudiant;
import com.example.gestion_des_notes.Service.Apiprofreq;
import com.example.gestion_des_notes.SignUp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEtud extends AppCompatActivity {
    EditText cin,classe;
    Button bajout;
    Apietudiant apietudiant;
    RecyclerView recyclerView;
    ArrayList<Etudiant> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_etud);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cin=findViewById(R.id.tcin);
        classe=findViewById(R.id.tclasse);
        bajout=findViewById(R.id.btnajoutetud);
        apietudiant = Apiapp.getClient().create(Apietudiant.class);
        bajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int cinetud = Integer.parseInt(cin.getText().toString());
            String classetud = classe.getText().toString();
                if (String.valueOf(cinetud).length() != 8 || classetud.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Le champe cin doit etre 8 numero et le champe classe doit etre non vide", Toast.LENGTH_SHORT).show();
                }else {

                    Call<Void> call= apietudiant.addetudbyadmin(cinetud,classetud);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ActivityEtud.this, "Etudiant enregistré avec succès", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityEtud.this, "Etudiant alredy exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ActivityEtud.this, "Erreur de connexion!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        recyclerView=findViewById(R.id.recycleretud);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Etudiant>> call=apietudiant.getalletud();
        call.enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                list= (ArrayList<Etudiant>) response.body();
                EtudAdapter etudAdapter= new EtudAdapter(list);
                recyclerView.setAdapter(etudAdapter);

            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                Log.i("appsmart",t.toString());
                Toast.makeText(ActivityEtud.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}