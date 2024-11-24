package com.example.gestion_des_notes.Activityprof;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class Add_note extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
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
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void showPopupMenu(View anchor) {
        // Create a PopupMenu anchored to the clicked view
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.setGravity(Gravity.END);
        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Handle menu item clicks using if-else
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Show "Hello Home" toast when Home is clicked
                Intent i=new Intent(Add_note.this, Home_prof.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.addnote) {
                Intent i=new Intent(Add_note.this, Add_note.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.logout) {
                Intent i=new Intent(Add_note.this, MainActivity.class);
                startActivity(i);
            }
            return false;
        });

        // Show the popup menu
        popupMenu.show();
    }
}