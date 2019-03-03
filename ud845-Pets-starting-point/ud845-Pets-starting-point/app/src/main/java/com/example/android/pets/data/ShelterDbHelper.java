package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShelterDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shelter.db";

    public ShelterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ShelterContract.PetEntry.TABLE_NAME + " (" +
                        ShelterContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ShelterContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL," +
                        ShelterContract.PetEntry.COLUMN_PET_BREED + " TEXT," +
                        ShelterContract.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL," +
                        ShelterContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ShelterContract.PetEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
