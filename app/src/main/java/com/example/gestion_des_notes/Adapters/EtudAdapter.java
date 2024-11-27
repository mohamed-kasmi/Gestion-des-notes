package com.example.gestion_des_notes.Adapters;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apietudiant;
import com.example.gestion_des_notes.Service.Apiprofreq;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtudAdapter extends RecyclerView.Adapter<EtudViewholder>{
    List<Etudiant> list;
    Apietudiant apietudiant;
    Etudiant etudiant;

    public EtudAdapter(List<Etudiant> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public EtudViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etude,parent,false);
        return new EtudViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudViewholder holder, int position) {
        Etudiant etudiant= list.get(position);
        holder.bind(etudiant);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Etudiant");
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_etude, null);
                builder.setView(dialogView);
                TextView TextView1 = dialogView.findViewById(R.id.tcinetud);
                TextView textView = dialogView.findViewById(R.id.temailetud);
                textView.setText("Cin: " + etudiant.getCin());
                TextView1.setText("Class: " + etudiant.getClasse());
               builder.setPositiveButton("supprime", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       apietudiant = Apiapp.getClient().create(Apietudiant.class);
                       Call<Void> call=apietudiant.deletetud(etudiant.getCin());
                       call.enqueue(new Callback<Void>() {
                           @Override
                           public void onResponse(Call<Void> call, Response<Void> response) {
                               Toast.makeText(v.getContext(), "Etudiant a ete supprime", Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onFailure(Call<Void> call, Throwable t) {
                               Toast.makeText(v.getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                           }
                       });
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
