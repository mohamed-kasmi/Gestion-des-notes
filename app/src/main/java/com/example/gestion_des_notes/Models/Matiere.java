package com.example.gestion_des_notes.Models;

public class Matiere {
    private int id;
    private String matiere;
    private String classe;
    private double cofi;

    public Matiere() {
    }

    public Matiere(int id, String matiere, String classe, double cofi) {
        this.id = id;
        this.matiere = matiere;
        this.classe = classe;
        this.cofi = cofi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getCofi() {
        return cofi;
    }

    public void setCofi(double cofi) {
        this.cofi = cofi;
    }
}
