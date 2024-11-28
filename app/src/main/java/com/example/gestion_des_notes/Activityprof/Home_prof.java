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

        // Handle menu item clicks using a standard listener
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
        // Create a PopupMenu anchored to the clicked view
        PopupMenu popupMenu = new PopupMenu(this, anchor);

        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);

        // Handle menu item clicks using standard listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    // Navigate to Home_prof
                    Intent i = new Intent(Home_prof.this, Home_prof.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.addnote) {
                    // Navigate to Add_note
                    Intent i = new Intent(Home_prof.this, Add_note.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.logout) {
                    // Logout logic: clear "STAY_CONNECTED" from SharedPreferences
                    SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("STAY_CONNECTED", false); // Set STAY_CONNECTED to false
                    editor.apply();

                    // Navigate back to MainActivity
                    Intent i = new Intent(Home_prof.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                    startActivity(i);
                    finish(); // Close the current activity
                    return true;
                }
                return false;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }
}
