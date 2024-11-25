package com.example.gestion_des_notes.Models;

public class Prof_Req {
    private int cin;
    private String nom;
    private String prenom;
    private String gener;
    private String email;
    private String password;

    public Prof_Req(int cin, String nom, String prenom, String gener, String email, String password) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.gener = gener;
        this.email = email;
        this.password = password;
    }

    public Prof_Req() {
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
