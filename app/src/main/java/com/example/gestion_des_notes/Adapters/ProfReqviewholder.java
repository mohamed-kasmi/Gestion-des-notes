package com.example.gestion_des_notes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Prof_Req;
import com.example.gestion_des_notes.R;
import com.squareup.picasso.Picasso;

public class ProfReqviewholder extends RecyclerView.ViewHolder {
    TextView tcin,tname,tlastname,temail;
    public ProfReqviewholder(@NonNull View itemView) {
        super(itemView);
        tcin=itemView.findViewById(R.id.cinprofreq);
        tname=itemView.findViewById(R.id.nameprofreq);
        tlastname=itemView.findViewById(R.id.lastnameprofreq);
        temail=itemView.findViewById(R.id.emailprofreq);
    }
    public void bind(Prof_Req profReq){
        tcin.setText(String.valueOf(profReq.getCin()));
        tname.setText(profReq.getNom());
        tlastname.setText(profReq.getPrenom());
        temail.setText(profReq.getEmail());

    }
}
