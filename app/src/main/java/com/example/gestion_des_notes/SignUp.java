package com.example.gestion_des_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_des_notes.Activityadmin.ActivityProfReq;
import com.example.gestion_des_notes.Models.Prof;
import com.example.gestion_des_notes.Models.Prof_Req;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apiprofreq;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    EditText firstName, lastName, email, cin, password, confirmPassword;
    RadioGroup genderGroup, roleGroup;
    Button signUpButton;
    TextView loginLink;
    Apiprofreq apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        email = findViewById(R.id.editTextEmail);
        cin = findViewById(R.id.Cin);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        genderGroup = findViewById(R.id.Gendre);
        roleGroup = findViewById(R.id.radioGroupRole);
        signUpButton = findViewById(R.id.buttonSignUp);
        loginLink = findViewById(R.id.log);

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, ActivityProfReq.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> handleSignUp());
    }

    private void handleSignUp() {
        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();
        String emailText = email.getText().toString();
        int cinNumber = Integer.parseInt(cin.getText().toString());
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();
        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() ||
                cinNumber==0 || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(this, "Les mots de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        RadioButton selectedGender = findViewById(selectedGenderId);
        String gender = (selectedGender != null) ? selectedGender.getText().toString() : "";

        int selectedRoleId = roleGroup.getCheckedRadioButtonId();
        RadioButton selectedRole = findViewById(selectedRoleId);

        if (selectedRole == null) {
            Toast.makeText(this, "Veuillez sélectionner un rôle.", Toast.LENGTH_SHORT).show();
            return;
        }

        String role = selectedRole.getText().toString();
        Prof_Req profReq = new Prof_Req(cinNumber,firstNameText,lastNameText,gender,emailText,passwordText);


        apiService = Apiapp.getClient().create(Apiprofreq.class);
        Call<Void> call = apiService.addprofreq(cinNumber,firstNameText,lastNameText,gender,emailText,passwordText);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Utilisateur enregistré avec succès!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "This user alredy exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUp.this, "Erreur de connexion.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
