package com.example.gestion_des_notes.Models;

public class ProfSQL {
    private int id;
    private byte[] image;

    public ProfSQL() {
    }

    public ProfSQL(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
