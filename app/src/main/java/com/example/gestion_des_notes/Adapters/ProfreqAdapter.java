package com.example.gestion_des_notes.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Activityadmin.ActivityProfReq;
import com.example.gestion_des_notes.Models.Prof;
import com.example.gestion_des_notes.Models.Prof_Req;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apiprof;
import com.example.gestion_des_notes.Service.Apiprofreq;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfreqAdapter extends RecyclerView.Adapter<ProfReqviewholder> {
    List<Prof_Req> list;
    Apiprofreq apiprofreq;
    Apiprof apiprof;
    public ProfreqAdapter(List<Prof_Req> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProfReqviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profreq,parent,false);
        return new ProfReqviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfReqviewholder holder, int position) {
        Prof_Req profReq = list.get(position);
        holder.bind(profReq);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation Enseignant");
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_profreq, null);
                builder.setView(dialogView);
                TextView textView = dialogView.findViewById(R.id.temail);
                TextView TextView1 = dialogView.findViewById(R.id.tcin);
                textView.setText("Cin: " + profReq.getCin());
                TextView1.setText("Email: " + profReq.getEmail());
                builder.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiprof = Apiapp.getClient().create(Apiprof.class);
                        Call<Void> call= apiprof.addprof(profReq.getCin(), profReq.getNom(), profReq.getPrenom(),
                                profReq.getGener(), profReq.getEmail(), profReq.getPassword());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(v.getContext(), "Enseignant a ete ajoute" + profReq.getEmail(), Toast.LENGTH_SHORT).show();
                                apiprofreq = Apiapp.getClient().create(Apiprofreq.class);
                                Call<Void> call1 = apiprofreq.deleteProfreq(profReq.getCin());
                                call1.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent i=new Intent(v.getContext(), ActivityProfReq.class);
                                        ContextCompat.startActivity(v.getContext(),i,null);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Erreur" , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Rfuser", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiprofreq = Apiapp.getClient().create(Apiprofreq.class);
                        Call<Void> call = apiprofreq.deleteProfreq(profReq.getCin());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(v.getContext(), "Enseignant a ete supprime" + profReq.getEmail(), Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(v.getContext(), ActivityProfReq.class);
                                    ContextCompat.startActivity(v.getContext(),i,null);
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Erreur" , Toast.LENGTH_SHORT).show();
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
