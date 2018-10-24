package ru.geekbrains.multiweather.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;

public class CitiesDataSource implements Closeable {

    private Database database;
    private SQLiteDatabase sqLiteDatabase;
    private CitiesDataReader citiesDataReader;

    public CitiesDataSource(Context context){
        database = new Database(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = database.getWritableDatabase();
        citiesDataReader = new CitiesDataReader(sqLiteDatabase);
        citiesDataReader.open();
    }

    @Override
    public void close() throws IOException {
        citiesDataReader.close();
        database.close();
    }

    public Cities addCity(String city){
        ContentValues values = new ContentValues();
        values.put(database.CITIES_COLUMN_CITY,city);
        long insertid = sqLiteDatabase.insert(database.TABLE_CITIES,null,values);
        Cities cities = new Cities();
        cities.setCity(city);
        cities.setId(insertid);
        return cities;
    }

    public void editCity(Cities cities, String city){
        ContentValues values = new ContentValues();
        values.put(database.CITIES_COLUMN_ID, cities.getId());
        values.put(database.CITIES_COLUMN_CITY, city);
        sqLiteDatabase.update(database.TABLE_CITIES,values,database.CITIES_COLUMN_ID +
                " = " + cities.getId(),null);
    }

    public void deleteAll(Cities cities){
        long id = cities.getId();
        sqLiteDatabase.delete(database.TABLE_CITIES,database.CITIES_COLUMN_ID + " = " +
                id,null);
    }

    public CitiesDataReader getCitiesDataReader() {
        return citiesDataReader;
    }
}
