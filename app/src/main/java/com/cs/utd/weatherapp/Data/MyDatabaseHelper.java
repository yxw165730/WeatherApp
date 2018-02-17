package com.cs.utd.weatherapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cs.utd.weatherapp.LocationInfo;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "weather.db";

    public  MyDatabaseHelper(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + LocationInfo.TABLE + "("
                + LocationInfo.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + LocationInfo.KEY_location + " TEXT )";

        db.execSQL(CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocationInfo.TABLE);

        onCreate(db);
    }

    public void addLocation(String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocationInfo.KEY_location, location);

        db.insert(LocationInfo.TABLE, null, values);
        db.close();
        //Log.v("TAG", "-----------------------------Insert data successfully!-----------------------------");
    }

    public List<String> getAllLocations() {

        List<String> mData = new ArrayList<>();
        String selectQuery = "SELECT " +
                LocationInfo.KEY_location +
                " FROM " + LocationInfo.TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String location = cursor.getString(cursor.getColumnIndex(LocationInfo.KEY_location));
                mData.add(location);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        //Log.v("TAG", "-----------------------------Retrieve data successfully!-----------------------------");
        return mData;
    }
}
