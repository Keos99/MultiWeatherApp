package ru.geekbrains.multiweather.bd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CitiesDataReader {
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    private String[] citiesAllColumns = {
            Database.CITIES_COLUMN_ID,
            Database.CITIES_COLUMN_CITY
    };

    public CitiesDataReader(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void open(){
        query();
        cursor.moveToFirst();
    }

    public void close(){
        cursor.close();
    }

    public void refresh(){
        int position = cursor.getPosition();
        query();
        cursor.moveToPosition(position);
    }

    private void query(){
        cursor = sqLiteDatabase.query(Database.TABLE_CITIES,citiesAllColumns,null,
                null,null,null,null);
    }

    public Cities getPosition (int position){
        cursor.moveToPosition(position);
        return cursorToCities();
    }

    public Cities cursorToCities(){
        Cities cities = new Cities();
        cities.setId(cursor.getLong(0));
        cities.setCity(cursor.getString(1));
        return cities;
    }
}
