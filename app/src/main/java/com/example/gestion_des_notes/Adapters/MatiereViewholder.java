package com.example.gestion_des_notes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Etudiant;
import com.example.gestion_des_notes.Models.Matiere;
import com.example.gestion_des_notes.R;

public class MatiereViewholder extends RecyclerView.ViewHolder {
    TextView tmatiere,tsession,tcof;
    public MatiereViewholder(@NonNull View itemView) {
        super(itemView);
        tmatiere=itemView.findViewById(R.id.itemmatiere);
        tsession=itemView.findViewById(R.id.itemsesson);
        tcof=itemView.findViewById(R.id.itemcofi);
    }
    public void bind(Matiere matiere){
        tmatiere.setText("Matiere: "+matiere.getMatiere());
        tsession.setText("Section: "+matiere.getClasse());
        tcof.setText("Coefficient: "+matiere.getCofi());

    }
}
