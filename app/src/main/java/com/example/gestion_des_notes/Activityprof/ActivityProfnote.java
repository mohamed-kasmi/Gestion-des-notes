package com.example.gestion_des_notes.Activityprof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Adapters.ProfnotesAdapter;
import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apinotes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfnote extends AppCompatActivity {
    EditText tcin;
    Button button;
    RecyclerView recyclerView;
    Apinotes apinotes;
    ArrayList<Notes> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profnote);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        tcin=findViewById(R.id.cinetud);
        button=findViewById(R.id.buttoncherchenote);
        recyclerView=findViewById(R.id.recyclernotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apinotes= Apiapp.getClient().create(Apinotes.class);
        SharedPreferences sp=getSharedPreferences("UserPref",MODE_PRIVATE);
        String CinP=sp.getString("CIN",null);

        int Cin2=Integer.parseInt(CinP);
        Call<List<Notes>> call=apinotes.getallbycinprof(Cin2);
        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                list= (ArrayList<Notes>) response.body();
                ProfnotesAdapter profnotesAdapter= new ProfnotesAdapter(list);
                recyclerView.setAdapter(profnotesAdapter);
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {
                Toast.makeText(ActivityProfnote.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tcin1=tcin.getText().toString();
                if(tcin1.isEmpty()){
                    Call<List<Notes>> call=apinotes.getallbycinprof(223322);
                    call.enqueue(new Callback<List<Notes>>() {
                        @Override
                        public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                            list= (ArrayList<Notes>) response.body();
                            ProfnotesAdapter profnotesAdapter= new ProfnotesAdapter(list);
                            recyclerView.setAdapter(profnotesAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Notes>> call, Throwable t) {
                            Toast.makeText(ActivityProfnote.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    int cin=Integer.parseInt(tcin1);
                    Call<List<Notes>> call=apinotes.findbycinetud(cin,223322);
                    call.enqueue(new Callback<List<Notes>>() {
                        @Override
                        public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                            list= (ArrayList<Notes>) response.body();
                            ProfnotesAdapter profnotesAdapter= new ProfnotesAdapter(list);
                            recyclerView.setAdapter(profnotesAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Notes>> call, Throwable t) {
                            Toast.makeText(ActivityProfnote.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void showPopupMenu(View anchor) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // Create a PopupMenu anchored to the clicked view
        PopupMenu popupMenu = new PopupMenu(this, anchor);

        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        // Handle menu item clicks using if-else
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Show "Hello Home" toast when Home is clicked
                Intent i=new Intent(ActivityProfnote.this, Home_prof.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.Profnote) {
                Intent i=new Intent(ActivityProfnote.this, ActivityProfnote.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.addnote) {
                Intent i=new Intent(ActivityProfnote.this, ActivityAddnote.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.logout) {
                vibrator.vibrate(500);
                Intent i=new Intent(ActivityProfnote.this, MainActivity.class);
                startActivity(i);
                return true;
            }
            return false;
        });

        // Show the popup menu
        popupMenu.show();
    }


}
