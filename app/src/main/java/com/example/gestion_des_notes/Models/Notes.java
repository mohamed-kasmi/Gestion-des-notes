package com.example.gestion_des_notes.Models;

public class Notes {
    private int id;
    private int cinetud;
    private int cinprof;
    private String matiere;
    private String classe;
    private String type;
    private double note;

    public Notes() {
    }

    public Notes(int id, int cinetud, int cinprof, String matiere, String classe, String type, double note) {
        this.id = id;
        this.cinetud = cinetud;
        this.cinprof = cinprof;
        this.matiere = matiere;
        this.classe = classe;
        this.type = type;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinetud() {
        return cinetud;
    }

    public void setCinetud(int cinetud) {
        this.cinetud = cinetud;
    }

    public int getCinprof() {
        return cinprof;
    }

    public void setCinprof(int cinprof) {
        this.cinprof = cinprof;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
