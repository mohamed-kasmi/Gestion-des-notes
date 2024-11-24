package com.example.gestion_des_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

public class Prof extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

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
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void showPopupMenu(View anchor) {
        // Create a PopupMenu anchored to the clicked view
        PopupMenu popupMenu = new PopupMenu(this, anchor);

        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Handle menu item clicks using if-else
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Show "Hello Home" toast when Home is clicked
                Intent i=new Intent(Prof.this, Home_prof.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.addnote) {
                Intent i=new Intent(Prof.this, Add_note.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.logout) {
                Intent i=new Intent(Prof.this, MainActivity.class);
                startActivity(i);
                return true;
            }
            return false;
        });

        // Show the popup menu
        popupMenu.show();
    }


}
