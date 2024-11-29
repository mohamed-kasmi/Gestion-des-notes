package com.example.gestion_des_notes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.R;

public class ProfnotesViewholder extends RecyclerView.ViewHolder {
    TextView tcin,tmatiere,ttype,tnote;

    public ProfnotesViewholder(@NonNull View itemView) {
        super(itemView);
        tcin=itemView.findViewById(R.id.itemnotesetudcin);
        tmatiere=itemView.findViewById(R.id.itemnotesetudmatiere);
        ttype=itemView.findViewById(R.id.itemnotesetudtype);
        tnote=itemView.findViewById(R.id.itemnotesetudnote);
    }
    public void bind(Notes notes){
        tcin.setText("Cin: "+notes.getCinetud());
        tmatiere.setText("Matiere: "+notes.getMatiere());
        ttype.setText("Type: "+notes.getType());
        tnote.setText("Note: "+notes.getNote());
    }
}
