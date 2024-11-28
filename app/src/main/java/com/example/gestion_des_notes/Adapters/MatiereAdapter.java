package com.example.gestion_des_notes.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Activityadmin.ActivityProfReq;
import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Matiere;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apimatiere;
import com.example.gestion_des_notes.Service.Apiprof;
import com.example.gestion_des_notes.Service.Apiprofreq;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatiereAdapter extends RecyclerView.Adapter<MatiereViewholder> {
    List<Matiere> list;
    Apimatiere apimatiere;

    public MatiereAdapter(List<Matiere> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MatiereViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matiere,parent,false);
        return new MatiereViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatiereViewholder holder, int position) {
        Matiere matiere= list.get(position);
        holder.bind(matiere);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Modifier Matiere");
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_matiere, null);
                builder.setView(dialogView);
                TextView textView = dialogView.findViewById(R.id.dialogmatiere);
                TextView TextView1 = dialogView.findViewById(R.id.dialogclasse);
                TextView TextView2 = dialogView.findViewById(R.id.dialogcofi);
                textView.setText("Matiere: " + matiere.getMatiere());
                TextView1.setText("Section: " + matiere.getClasse());
                TextView2.setText("Coefficient: " + matiere.getCofi());
                EditText mat=dialogView.findViewById(R.id.updatematiere);
                EditText ses=dialogView.findViewById(R.id.updatesection);
                EditText cofi=dialogView.findViewById(R.id.updatecofi);


                builder.setPositiveButton("Mettre a jour", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String matt=mat.getText().toString();
                        String section=ses.getText().toString();
                        String cofii=cofi.getText().toString();
                        if (matt.isEmpty() || section.isEmpty() || cofii.isEmpty()) {
                            // Show a Toast if any of the fields are empty
                            Toast.makeText(v.getContext(), "verifier tous les champes.", Toast.LENGTH_SHORT).show();
                        }else {
                        double cofDouble = Double.parseDouble(cofii);

                        Matiere matiere1=new Matiere(matt,section,cofDouble);
                        apimatiere=Apiapp.getClient().create(Apimatiere.class);
                        int idmatiere=matiere.getId();
                        Call<Void> call=apimatiere.updatematiere(idmatiere,matiere1);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(v.getContext(), "Mettre a jour avec sucsse.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Erreur de connexion.", Toast.LENGTH_SHORT).show();
                            }
                        });}


                    }
                });

                builder.setNegativeButton("Rfuser", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
