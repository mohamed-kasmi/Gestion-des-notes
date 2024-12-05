package com.example.gestion_des_notes.Activityetud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Activityprof.ActivityAddnote;
import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.Adapters.EtudNoteAdapter;
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
    Button button, T;
    ArrayList<Notes> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_etud);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_your_icon1) {
                    showPopupMenu(findViewById(R.id.toolbar1));
                    return true;
                }
                return false;
            }
        });

        tche = findViewById(R.id.serchnoteetud);
        button = findViewById(R.id.searchmatiereetud);
        recyclerView = findViewById(R.id.recycleretudnote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apinotes = Apiapp.getClient().create(Apinotes.class);
        SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
        String cin = sp.getString("CIN_ETUD", null);
        int Cin1 = Integer.parseInt(cin);
        Call<List<Notes>> call = apinotes.getallbycinetude(Cin1);
        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                list = (ArrayList<Notes>) response.body();
                EtudNoteAdapter etudNoteAdapter = new EtudNoteAdapter(list);
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
                String matiere = tche.getText().toString();
                if (matiere.isEmpty()) {
                    Call<List<Notes>> callEmpty = apinotes.getallbycinetude(Cin1);
                    callEmpty.enqueue(new Callback<List<Notes>>() {
                        @Override
                        public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                            list = (ArrayList<Notes>) response.body();
                            EtudNoteAdapter etudNoteAdapter = new EtudNoteAdapter(list);
                            recyclerView.setAdapter(etudNoteAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Notes>> call, Throwable t) {
                            Toast.makeText(ActivityNoteEtud.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<List<Notes>> callMatiere = apinotes.getallbycinetudandmatiere(Cin1, matiere);
                    callMatiere.enqueue(new Callback<List<Notes>>() {
                        @Override
                        public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                            list = (ArrayList<Notes>) response.body();
                            EtudNoteAdapter etudNoteAdapter = new EtudNoteAdapter(list);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuetud, menu);
        return true;
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenuetud = new PopupMenu(this, anchor);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        popupMenuetud.getMenuInflater().inflate(R.menu.popup_menuetud, popupMenuetud.getMenu());
        popupMenuetud.setGravity(Gravity.END);

        popupMenuetud.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout1) {
                    SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("STAY_CONNECTED_E", false);
                    editor.apply();
                    vibrator.vibrate(500);
                    Intent i = new Intent(ActivityNoteEtud.this, MainActivity.class);
                    startActivity(i);
                    return true;
            }
                return false;
        }


        });

        popupMenuetud.show();
    }
}
