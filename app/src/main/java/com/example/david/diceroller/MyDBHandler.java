package com.example.david.diceroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 12/29/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    // Basic properties of the database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "presets.db";

    // Corresponding columns based on the fields in DicePresets.java
    public static final String TABLE_PRESETS = "presets";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRESET_NAME = "_presetName";
    public static final String COLUMN_DICE_NUM = "_diceNum";
    public static final String COLUMN_DICE_BONUS = "_diceBonus";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Because databases use SQL format and I have no experience with it, I can't really
        // explain all this very well. I followed a guide online on using SQL.
        String query = " CREATE TABLE " + TABLE_PRESETS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_PRESET_NAME + " TEXT " +
                COLUMN_DICE_NUM + " ARRAY " +
                COLUMN_DICE_BONUS + " ARRAY " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS" + TABLE_PRESETS);
        onCreate(db);
    }

    public void addPreset(DicePresets preset) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRESET_NAME, preset.get_presetName());

        // How to get the array values into the Database?

        db.insert(TABLE_PRESETS, null, values);
        db.close();
    }

    public void deletePreset(String presetName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRESETS + " WHERE " + COLUMN_PRESET_NAME + "=\"" + presetName + "\";");
    }
}
