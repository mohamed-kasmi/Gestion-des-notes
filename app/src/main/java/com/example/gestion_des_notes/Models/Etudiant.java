package com.example.gestion_des_notes.Models;

public class Etudiant {
    private int cin;
    private String nom;
    private String prenom;
    private String genre;
    private String email;
    private String password;
    private String classe;

    public Etudiant() {
    }

    public Etudiant(int cin, String nom, String prenom, String genre, String email, String password, String classe) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.email = email;
        this.password = password;
        this.classe = classe;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
