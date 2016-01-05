package com.example.david.diceroller2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 12/29/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    // Basic properties of the database
    private static final int DATABASE_VERSION = 21;
    private static final String DATABASE_NAME = "presets.db";

    // Corresponding columns based on the fields in DicePresets.java
    public static final String TABLE_PRESETS = "presets";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRESET_NAME = "_presetName";
    // number of respective dice
    public static final String COLUMN_D4_NUM = "_d4Num";
    public static final String COLUMN_D6_NUM = "_d6Num";
    public static final String COLUMN_D8_NUM = "_d8Num";
    public static final String COLUMN_D10_NUM = "_d10Num";
    public static final String COLUMN_D12_NUM = "_d12Num";
    public static final String COLUMN_D20_NUM = "_d20Num";
    public static final String COLUMN_D100_NUM = "_d100Num";
    // bonus of respective dice
    public static final String COLUMN_D4_BONUS = "_d4Bonus";
    public static final String COLUMN_D6_BONUS = "_d6Bonus";
    public static final String COLUMN_D8_BONUS = "_d8Bonus";
    public static final String COLUMN_D10_BONUS = "_d10Bonus";
    public static final String COLUMN_D12_BONUS = "_d12Bonus";
    public static final String COLUMN_D20_BONUS = "_d20Bonus";
    public static final String COLUMN_D100_BONUS = "_d100Bonus";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_PRESETS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRESET_NAME + " TEXT," +
                COLUMN_D4_NUM + " INTEGER," +
                COLUMN_D6_NUM + " INTEGER," +
                COLUMN_D8_NUM + " INTEGER," +
                COLUMN_D10_NUM + " INTEGER," +
                COLUMN_D12_NUM + " INTEGER," +
                COLUMN_D20_NUM + " INTEGER," +
                COLUMN_D100_NUM + " INTEGER," +
                COLUMN_D4_BONUS + " INTEGER," +
                COLUMN_D6_BONUS + " INTEGER," +
                COLUMN_D8_BONUS + " INTEGER," +
                COLUMN_D10_BONUS + " INTEGER," +
                COLUMN_D12_BONUS + " INTEGER," +
                COLUMN_D20_BONUS + " INTEGER," +
                COLUMN_D100_BONUS + " INTEGER" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESETS );
        onCreate(db);
    }

    public void addPreset(DicePresets preset) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRESET_NAME, preset.get_presetName());
        // put in number of respective dice into table
        values.put(COLUMN_D4_NUM, preset.get_d4Num());
        values.put(COLUMN_D6_NUM, preset.get_d6Num());
        values.put(COLUMN_D8_NUM, preset.get_d8Num());
        values.put(COLUMN_D10_NUM, preset.get_d10Num());
        values.put(COLUMN_D12_NUM, preset.get_d12Num());
        values.put(COLUMN_D20_NUM, preset.get_d20Num());
        values.put(COLUMN_D100_NUM, preset.get_d100Num());
        // put in bonus of respective dice into table
        values.put(COLUMN_D4_BONUS, preset.get_d4Bonus());
        values.put(COLUMN_D6_BONUS, preset.get_d6Bonus());
        values.put(COLUMN_D8_BONUS, preset.get_d8Bonus());
        values.put(COLUMN_D10_BONUS, preset.get_d10Bonus());
        values.put(COLUMN_D12_BONUS, preset.get_d12Bonus());
        values.put(COLUMN_D20_BONUS, preset.get_d20Bonus());
        values.put(COLUMN_D100_BONUS, preset.get_d100Bonus());
        // commit these changes to the name of the user's preset.
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRESETS, null, values);
        db.close();
    }

    public void deletePreset(String presetName) {
        // for the time being, this method of deletion works only when entering
        // the name. It will need to be modified so that it works with the delete button instead.
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRESETS + " WHERE " + COLUMN_PRESET_NAME + "=\"" + presetName + "\";");
    }

    public int[] getPreset(String presetName) {
        // method to grab a DicePresets object from the table
        int[] data = new int[14];
        String selectQuery = "SELECT  * FROM " + TABLE_PRESETS + " WHERE " + COLUMN_PRESET_NAME + "=\"" + presetName + "\";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (!cursor.isAfterLast()) {
            data[0] = cursor.getInt(cursor.getColumnIndex(presetName));
        }
        db.close();
        return data;
    }
}

