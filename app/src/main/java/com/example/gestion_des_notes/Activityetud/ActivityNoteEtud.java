package com.example.gestion_des_notes.Activityetud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.R;

public class ActivityNoteEtud extends AppCompatActivity {
    TextView T;

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

        // Initialize the logout button
        T = findViewById(R.id.Title); // Assuming the button ID is buttonLogout

        // Set the click listener for the logout button
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Access SharedPreferences
                SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                // Set STAY_CONNECTED to false
                editor.putBoolean("STAY_CONNECTED", false);
                editor.apply();

                // Navigate back to MainActivity
                Intent i = new Intent(ActivityNoteEtud.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                startActivity(i);

                // Finish the current activity
                finish();
            }
        });
    }
}
