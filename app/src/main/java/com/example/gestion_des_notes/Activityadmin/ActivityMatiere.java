package com.example.gestion_des_notes.Activityadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Adapters.EtudAdapter;
import com.example.gestion_des_notes.Adapters.MatiereAdapter;
import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Matiere;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apimatiere;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMatiere extends AppCompatActivity {
Apimatiere apimatiere;
RadioGroup radioGroup;
RadioButton rit,rsem2,rsem3,rdsi2,rdsi3;
EditText tmatiere,tcof;
Button bajouter;
RecyclerView recyclerView;
ArrayList<Matiere> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matiere);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        radioGroup=findViewById(R.id.rid);
        rit=findViewById(R.id.rit);
        rsem2=findViewById(R.id.rsem2);
        rsem3=findViewById(R.id.rsem3);
        rdsi2=findViewById(R.id.rdsi2);
        rdsi3=findViewById(R.id.rdsi3);
        tmatiere=findViewById(R.id.namematiere);
        tcof=findViewById(R.id.matierecofi);
        bajouter=findViewById(R.id.ajoutmatieree);
        bajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String session="";
                if (rit.isChecked()){
                    session="IT";
                } else if (rsem2.isChecked()) {
                    session="SEM2";
                } else if (rsem3.isChecked()) {
                    session="SEM3";
                } else if (rdsi2.isChecked()) {
                    session="DSI2";
                } else if (rdsi3.isChecked()) {
                    session="DSI3";
                }
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String matiere = tmatiere.getText().toString();
                String cofString = tcof.getText().toString();
                if (selectedId == -1 || matiere.isEmpty() || cofString.isEmpty() || Double.parseDouble(cofString) == 0.0) {
                    Toast.makeText(ActivityMatiere.this, "Remplir tous les champs.", Toast.LENGTH_SHORT).show();
                }
                double cof=Double.parseDouble(cofString);

                apimatiere = Apiapp.getClient().create(Apimatiere.class);
                Call<Void> call= apimatiere.addmatiere(matiere,session,cof);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ActivityMatiere.this, "Matiere a ete ajouter.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ActivityMatiere.this, "Erreur de connexion.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        recyclerView=findViewById(R.id.recycleretud);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apimatiere = Apiapp.getClient().create(Apimatiere.class);
        Call<List<Matiere>> call=apimatiere.getallmatiere();
        call.enqueue(new Callback<List<Matiere>>() {
            @Override
            public void onResponse(Call<List<Matiere>> call, Response<List<Matiere>> response) {
                list= (ArrayList<Matiere>) response.body();
                MatiereAdapter matiereAdapter= new MatiereAdapter(list);
                recyclerView.setAdapter(matiereAdapter);
            }

            @Override
            public void onFailure(Call<List<Matiere>> call, Throwable t) {
                Toast.makeText(ActivityMatiere.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });

    }
}