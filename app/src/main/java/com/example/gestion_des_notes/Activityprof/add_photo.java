package com.example.gestion_des_notes.Activityprof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestion_des_notes.DataBase.ProfDataBase;
import com.example.gestion_des_notes.Models.ProfSQL;
import com.example.gestion_des_notes.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class add_photo extends AppCompatActivity {
    ImageView image;
    Bitmap Img;
    Button btn_add;
    ProfDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_photo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        image=findViewById(R.id.immg);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
            }
        });


        btn_add=findViewById(R.id.BT);
        SharedPreferences sp = getSharedPreferences("UserPref", MODE_PRIVATE);
        String cin=sp.getString("CIN",null);
        int cinP=Integer.parseInt(cin);
        ProfDataBase db = new ProfDataBase(add_photo.this);
        if (db.findIdById(cinP) != null){
            btn_add.setText("Mettre a jour");
        }else {
            btn_add.setText("Ajouter");
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfDataBase db = new ProfDataBase(add_photo.this);
                if (db.findIdById(cinP) != null){
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    Img.compress(Bitmap.CompressFormat.PNG, 0,byteArrayOutputStream);
                    byte[] bytesImage = byteArrayOutputStream.toByteArray();
                    ProfSQL country=new ProfSQL(bytesImage);
                    db.updateCountry(country,cinP);
                    Intent i =new Intent(add_photo.this, Home_prof.class);
                    startActivity(i);
                }else {
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    Img.compress(Bitmap.CompressFormat.PNG, 0,byteArrayOutputStream);
                    byte[] bytesImage = byteArrayOutputStream.toByteArray();
                    ProfSQL country=new ProfSQL(bytesImage);
                    if(db.addPhoto(country)){
                        Toast.makeText(add_photo.this,"Country added",Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(add_photo.this, Home_prof.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(add_photo.this,"don't",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==100)&&(resultCode==RESULT_OK)){
            Uri selectedImage=data.getData();
            image.setImageURI(selectedImage);
            try {
                Img= MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}