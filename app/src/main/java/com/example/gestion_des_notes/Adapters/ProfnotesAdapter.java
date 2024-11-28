package com.example.gestion_des_notes.Adapters;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_des_notes.Activityadmin.ActivityMatiere;
import com.example.gestion_des_notes.Models.Notes;
import com.example.gestion_des_notes.R;
import com.example.gestion_des_notes.Service.Apiapp;
import com.example.gestion_des_notes.Service.Apinotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfnotesAdapter extends RecyclerView.Adapter<ProfnotesViewholder> {
    List<Notes> list;
    Apinotes apinotes;

    public ProfnotesAdapter(List<Notes> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProfnotesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes,parent,false);
        return new ProfnotesViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfnotesViewholder holder, int position) {
        Notes notes=list.get(position);
        holder.bind(notes);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Note");
                apinotes= Apiapp.getClient().create(Apinotes.class);
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.gialog_note, null);
                TextView textView = dialogView.findViewById(R.id.dialognotecin);
                TextView TextView1 = dialogView.findViewById(R.id.dialognotematiere);
                TextView TextView2 = dialogView.findViewById(R.id.dialognotetype);
                TextView TextView3 = dialogView.findViewById(R.id.dialognotenote);
                textView.setText("CIN: " + notes.getCinetud());
                TextView1.setText("Matiere: " + notes.getMatiere());
                TextView2.setText("Type: " + notes.getType());
                TextView3.setText("Note: " + notes.getNote());
                RadioGroup radioGroup=dialogView.findViewById(R.id.dialogupadtenotetype);
                RadioButton radioButton=dialogView.findViewById(R.id.dialogupdatenotesradio);
                RadioButton radioButton1=dialogView.findViewById(R.id.dialogupdatenotesradio2);
                RadioButton radioButton2=dialogView.findViewById(R.id.dialogupdatenotesradio3);
                EditText editText=dialogView.findViewById(R.id.dialogupdatenotesnote);
                builder.setView(dialogView);
                builder.setPositiveButton("Mettre a jour", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type="";
                        if (radioButton.isChecked()){
                            type="DS";
                        } else if (radioButton1.isChecked()) {
                            type="TP";
                        } else if (radioButton2.isChecked()) {
                            type="EX";
                        }
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        String not = editText.getText().toString();
                        if (selectedId == -1 || not.isEmpty() ||  Double.parseDouble(not) == 0.0) {
                            Toast.makeText(v.getContext(), "Remplir tous les champs.", Toast.LENGTH_SHORT).show();
                        }
                        double notw=Double.parseDouble(not);
                        Notes notes1=new Notes(type,notw);
                        Call<Void> call=apinotes.updatenote(notes.getId(), notes1);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(v.getContext(), "Note a ete modifier.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Erreur de connexion.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> call=apinotes.deletenote(notes.getId());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(v.getContext(), "Note a ete supprimer.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Erreur de connexion.", Toast.LENGTH_SHORT).show();
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
