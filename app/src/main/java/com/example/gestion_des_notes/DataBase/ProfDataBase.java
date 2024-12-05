package com.example.gestion_des_notes.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.gestion_des_notes.Models.ProfSQL;

public class ProfDataBase extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Context context;
    public Boolean addPhoto(ProfSQL c){
        boolean result;
        ProfDataBase db1 = new ProfDataBase(context);
        String cin = db1.getSharedPreferenceValue("CIN", null);
        int cinP=Integer.parseInt(cin);
        db=this.getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put(CID,cinP);
        v.put(CIMAGE,c.getImage());
        long i=db.insert(TCountry,null,v);
        if(i==-1){
            result=false;
        }
        else{
            result=true;
        }
        return result;
    }
    public ProfSQL getCountryById(int id) {
        ProfSQL profSQL = null;
        db = this.getReadableDatabase();
        Cursor c = db.query(
                TCountry,
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (c != null && c.moveToFirst()) {
            profSQL = new ProfSQL();
            profSQL.setId(c.getInt(0));
            profSQL.setImage(c.getBlob(1));
        }
        if (c != null) {
            c.close();
        }

        return profSQL;
    }
    public boolean x(){
        return true;
    }
    public Integer findIdById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer foundId = null;
        Cursor cursor = db.query(
                TCountry,
                new String[]{CID},
                CID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            foundId = cursor.getInt(cursor.getColumnIndexOrThrow(CID));
            cursor.close();
        }
        db.close();
        return foundId;
    }


    public void updateCountry(ProfSQL c, int id){

        db=this.getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put(CIMAGE,c.getImage());
        db.update(TCountry,v, CID + " = ? ",new String[]{String.valueOf(id)});
    }
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME= "Prof_db";
    public static final String TCountry = "Prof";
    public static final String CID = "id";
    public static final String CIMAGE = "image";
    public static final String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TCountry + "("
            + CID + " INTEGER PRIMARY KEY, "
            + CIMAGE + " BLOB ) ";
    public ProfDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TCountry);
        onCreate(sqLiteDatabase);
    }
    public String getSharedPreferenceValue(String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }
}
