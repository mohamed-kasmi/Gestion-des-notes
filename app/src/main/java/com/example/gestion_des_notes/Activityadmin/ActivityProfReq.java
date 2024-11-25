package com.example.gestion_des_notes.Activityadmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Adapters.ProfreqAdapter;
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
}