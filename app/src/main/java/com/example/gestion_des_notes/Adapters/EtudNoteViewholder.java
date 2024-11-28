package com.example.gestion_des_notes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.R;

public class EtudNoteViewholder extends RecyclerView.ViewHolder {
    TextView tmaiere,ttype,tnote;

    public EtudNoteViewholder(@NonNull View itemView) {
        super(itemView);
        tmaiere=itemView.findViewById(R.id.itemnoteetudmatiere);
        ttype=itemView.findViewById(R.id.itemnoteetudtype);
        tnote=itemView.findViewById(R.id.itemnoteetudnote);
    }
    public void bind(Notes notes){
        tmaiere.setText("Matiere: "+notes.getMatiere());
        ttype.setText("Type: "+notes.getType());
        tnote.setText("Note: "+notes.getNote());
    }
}
