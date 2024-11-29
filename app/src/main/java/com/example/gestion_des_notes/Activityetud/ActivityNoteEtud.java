package com.example.gestion_des_notes.Activityetud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.Adapters.EtudNoteAdapter;
import com.example.gestion_des_notes.Models.Matiere;
import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apinotes;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityNoteEtud extends AppCompatActivity {
RecyclerView recyclerView;
Apinotes apinotes;
EditText tche;
Button button,T;
ArrayList<Notes> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_etud);

        // Apply system bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        T = findViewById(R.id.t1);
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("STAY_CONNECTED", false);
                editor.apply();
                Intent i = new Intent(ActivityNoteEtud.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
        tche=findViewById(R.id.serchnoteetud);
        button=findViewById(R.id.searchmatiereetud);
        recyclerView=findViewById(R.id.recycleretudnote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apinotes= Apiapp.getClient().create(Apinotes.class);
        Call<List<Notes>> call=apinotes.getallbycinetude(123123);
        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                list= (ArrayList<Notes>) response.body();
                EtudNoteAdapter etudNoteAdapter= new EtudNoteAdapter(list);
                recyclerView.setAdapter(etudNoteAdapter);
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {
                Toast.makeText(ActivityNoteEtud.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String matiere=tche.getText().toString();
            if (matiere.isEmpty()){
                apinotes= Apiapp.getClient().create(Apinotes.class);
                Call<List<Notes>> call=apinotes.getallbycinetude(123123);
                call.enqueue(new Callback<List<Notes>>() {
                    @Override
                    public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                        list= (ArrayList<Notes>) response.body();
                        EtudNoteAdapter etudNoteAdapter= new EtudNoteAdapter(list);
                        recyclerView.setAdapter(etudNoteAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Notes>> call, Throwable t) {
                        Toast.makeText(ActivityNoteEtud.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                apinotes= Apiapp.getClient().create(Apinotes.class);
                Call<List<Notes>> call=apinotes.getallbycinetudandmatiere(123123,matiere);
                call.enqueue(new Callback<List<Notes>>() {
                    @Override
                    public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                        list= (ArrayList<Notes>) response.body();
                        EtudNoteAdapter etudNoteAdapter= new EtudNoteAdapter(list);
                        recyclerView.setAdapter(etudNoteAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Notes>> call, Throwable t) {
                        Toast.makeText(ActivityNoteEtud.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
        });
    }
}