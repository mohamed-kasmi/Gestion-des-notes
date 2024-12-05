package com.example.gestion_des_notes.Activityadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Activityetud.ActivityNoteEtud;
import com.example.gestion_des_notes.Activityprof.ActivityAddnote;
import com.example.gestion_des_notes.Activityprof.ActivityProfnote;
import com.example.gestion_des_notes.Activityprof.Home_prof;
import com.example.gestion_des_notes.Adapters.ProfreqAdapter;
import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.Models.Prof;
import com.example.gestion_des_notes.Models.Prof_Req;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apiprofreq;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfReq extends AppCompatActivity {
    RecyclerView recyclerView;
    Apiprofreq apiprofreq;
    ArrayList<Prof_Req> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prof_req);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_your_icon2) {
                    showPopupMenu(findViewById(R.id.toolbar2));
                    return true;
                }
                return false;
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView=findViewById(R.id.recylerprofreq);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiprofreq = Apiapp.getClient().create(Apiprofreq.class);
        Call<List<Prof_Req>> call=apiprofreq.getProfreq();
        call.enqueue(new Callback<List<Prof_Req>>() {
            @Override
            public void onResponse(Call<List<Prof_Req>> call, Response<List<Prof_Req>> response) {
                list= (ArrayList<Prof_Req>) response.body();
                ProfreqAdapter profreqAdapter= new ProfreqAdapter(list);
                recyclerView.setAdapter(profreqAdapter);
            }

            @Override
            public void onFailure(Call<List<Prof_Req>> call, Throwable t) {
                Log.i("appsmart",t.toString());
                Toast.makeText(ActivityProfReq.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadmin, menu);
        return true;
    }
    private void showPopupMenu(View anchor) {
        PopupMenu popupMenuadmin = new PopupMenu(this, anchor);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        popupMenuadmin.getMenuInflater().inflate(R.menu.popup_menuadmin, popupMenuadmin.getMenu());
        popupMenuadmin.setGravity(Gravity.END);

        popupMenuadmin.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.etud) {
                    Intent i = new Intent(ActivityProfReq.this, ActivityEtud.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.mat) {
                    Intent i = new Intent(ActivityProfReq.this, ActivityMatiere.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.prof) {
                    Intent i = new Intent(ActivityProfReq.this, ActivityProfReq.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.logout1) {
                    vibrator.vibrate(500);
                    Intent i = new Intent(ActivityProfReq.this, MainActivity.class);
                    startActivity(i);
                    return true;
                }
                return false;
            }


        });

        popupMenuadmin.show();
    }
}