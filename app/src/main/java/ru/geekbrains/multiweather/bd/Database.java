package ru.geekbrains.multiweather.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "multiweather.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_CITIES = "cities";
    public static final String CITIES_COLUMN_ID = "_id";
    public static final String CITIES_COLUMN_CITY = "city";

    static final String TABLE_TEMPERATURE = "temperature";
    public static final String TEMPERATURE_COLUMN_ID = "_id";
    public static final String TEMPERATURE_COLUMN_CITY_ID = "city_id";
    public static final String TEMPERATURE_COLUMN_TEMPERATURE = "temperature";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CITIES + " (" + CITIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CITIES_COLUMN_CITY + " TEXT NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_TEMPERATURE + "( " + TEMPERATURE_COLUMN_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMPERATURE_COLUMN_CITY_ID +
                        " INTEGER REFERENCES cities (id) ON DELETE SET DEFAULT " +
                        " ON UPDATE SET DEFAULT NOT NULL DEFAULT (1)," + TEMPERATURE_COLUMN_TEMPERATURE +
                        " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
