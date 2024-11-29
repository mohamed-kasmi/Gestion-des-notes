package com.example.gestion_des_notes.Activityprof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.gestion_des_notes.MainActivity;
import com.example.gestion_des_notes.R;

public class Home_prof extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_prof);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set menu item click listener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                if (item.getItemId() == R.id.action_your_icon) {
                    // Show popup menu
                    showPopupMenu(findViewById(R.id.toolbar));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);

        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);

        // Set menu item click listener for the popup
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    // Navigate to Home_prof activity
                    Intent i = new Intent(Home_prof.this, Home_prof.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.addnote) {
                    // Navigate to ActivityAddnote
                    Intent i = new Intent(Home_prof.this, ActivityAddnote.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.logout) {
                    // Clear STAY_CONNECTED flag and navigate to MainActivity
                    SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("STAY_CONNECTED", false);
                    editor.apply();

                    Intent i = new Intent(Home_prof.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                    return true;
                }
                return false;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }
}
