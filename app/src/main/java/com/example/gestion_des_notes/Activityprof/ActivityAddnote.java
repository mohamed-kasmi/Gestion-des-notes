package com.example.gestion_des_notes.Activityprof;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.example.gestion_des_notes.Service.Apinotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddnote extends AppCompatActivity {
RadioButton rit1,rdsi2,rdsi3,rsem2,rsem3,typetp,typeds,typeex;
Spinner spinner;
Apimatiere apimatiere;
Apinotes apinotes;
EditText cinetud,noteajouter,classe;
Button button;
private String selectedOption;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_your_icon) {
                    showPopupMenu(findViewById(R.id.toolbar));
                    return true;
                }
                return false;
            }
        });
        apimatiere= Apiapp.getClient().create(Apimatiere.class);
        apinotes= Apiapp.getClient().create(Apinotes.class);
        rit1=findViewById(R.id.Section1);
        rdsi2=findViewById(R.id.Section2);
        rdsi3=findViewById(R.id.Section3);
        rsem2=findViewById(R.id.Section4);
        rsem3=findViewById(R.id.Section5);
        typetp=findViewById(R.id.type2);
        typeds=findViewById(R.id.type1);
        typeex=findViewById(R.id.type3);
        button=findViewById(R.id.addnotebtn);
        cinetud=findViewById(R.id.addnotecinetud);
        classe=findViewById(R.id.addnoteclasse);
        noteajouter=findViewById(R.id.addnotenote);
        spinner = findViewById(R.id.mySpinner);


        rit1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rit1.isChecked()){
                    fetchAndPopulateSpinner("IT");
               }
            }
        });
        rdsi2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rdsi2.isChecked())
                    fetchAndPopulateSpinner("DSI2");
            }
        });
        rdsi3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rdsi3.isChecked())
                    fetchAndPopulateSpinner("DSI3");
            }
        });
        rsem2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rsem2.isChecked())
                    fetchAndPopulateSpinner("SEM2");
            }
        });
        rsem3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rsem3.isChecked())
                    fetchAndPopulateSpinner("SEM3");
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ActivityAddnote.this, "No item selected", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double note=Double.parseDouble(noteajouter.getText().toString());
                int cin=Integer.parseInt(cinetud.getText().toString());
                String classee=classe.getText().toString();
                String typee="";
                if (typetp.isChecked()){  typee="TP";}
                if (typeds.isChecked()){ typee="DS";}
                if (typeex.isChecked()){ typee="EX";}
                SharedPreferences sp=getSharedPreferences("UserPref",MODE_PRIVATE);
                String CinP=sp.getString("CIN",null);
                int Cin2=Integer.parseInt(CinP);
                Call<Void> call=apinotes.addnote(cin,Cin2,selectedOption,classee,typee,note);/////////////////////////////////////////////////////////////////////////
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ActivityAddnote.this, "Note a ete ajouter.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ActivityAddnote.this, "Erreur de connexion.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private void fetchAndPopulateSpinner(String section){

        Call<List<String>> call=apimatiere.getmatierebyclasse(section);
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
        });
    }
    private void updateSpinner(List<String> options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
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
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        popupMenu.setGravity(Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());


        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.home) {

                Intent i=new Intent(ActivityAddnote.this, Home_prof.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.Profnote) {
                Intent i=new Intent(ActivityAddnote.this, ActivityProfnote.class);
                startActivity(i);
                return true;
            }else if (item.getItemId() == R.id.addnote) {
                Intent i=new Intent(ActivityAddnote.this, ActivityAddnote.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.logout) {
                vibrator.vibrate(500);
                Intent i=new Intent(ActivityAddnote.this, MainActivity.class);
                startActivity(i);
            }
            return false;
        });


        popupMenu.show();
    }
}