package com.example.gestion_des_notes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Prof_Req;
import com.example.gestion_des_notes.R;

public class EtudViewholder extends RecyclerView.ViewHolder{
    TextView tcin,tname,tlastname,temail;
    public EtudViewholder(@NonNull View itemView) {
        super(itemView);
        tcin=itemView.findViewById(R.id.cinetud);

        tlastname=itemView.findViewById(R.id.lastnameetud);
        temail=itemView.findViewById(R.id.emailetud);
    }
    public void bind(Etudiant etudiant){
        tcin.setText("CIN: "+String.valueOf(etudiant.getCin()));
        tlastname.setText("Email: "+etudiant.getEmail());
        temail.setText("Class: "+etudiant.getClasse());

    }
}
