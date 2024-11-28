package com.example.gestion_des_notes.Adapters;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Models.Matiere;
import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apimatiere;
import com.example.gestion_des_notes.Service.Apinotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtudNoteAdapter extends RecyclerView.Adapter<EtudNoteViewholder> {
    List<Notes> list;
    Apinotes apinotes;

    public EtudNoteAdapter(List<Notes> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public EtudNoteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etudnote,parent,false);
        return new EtudNoteViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudNoteViewholder holder, int position) {
        Notes notes=list.get(position);
        holder.bind(notes);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
