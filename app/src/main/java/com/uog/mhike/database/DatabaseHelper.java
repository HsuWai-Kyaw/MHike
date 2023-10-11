package com.uog.mhike.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mHike.db";
    private static final String TABLE_HIKE ="tblHike";
    private static final String TABLE_OBSERVATION = "tblObservation";

    private SQLiteDatabase database;

    private static final String CREATE_HIKE_TABLE =String.format(
            "CREATE TABLE IF NOT EXIST %s("+ //tblHike
                    " %s INTEGER PRIMARY KEY AUTOINCREMENT," + //id
                    " %s TEXT,"+ //name
                    " %s TEXT,"+ //location
                    " %s TEXT,"+ //date
                    " %s VARCHAR(3),"+ //parking
                    " %s REAL,"+ //length
                    " %s VARCHAR(128),"+ //difficulty
                    " %s TEXT,"+ //description
                    " %s TEXT,"+ //additional1
                    " %s TEXT,"+ //additional2
                    " %s REAL,"+ //additional num1
                    " %s REAL)" //additional num2
            , TABLE_HIKE,Hike.ID, Hike.NAME, Hike.LOCATION, Hike.DATE, Hike.PARKING , Hike.LENGTH, Hike.DIFFICULTY, Hike.DESCRIPTION, Hike.ADDITIONAL1, Hike.ADDITIONAL2, Hike.ADDITIONAL_NUM1,Hike.ADDITIONAL_NUM2);


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
        if (database != null) {
            database.execSQL("PRAGMA encoding ='UTF-8'");
            database.execSQL("PRAGMA foreign_keys = ON");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_HIKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long saveHike(Hike hike){
        long result = 0;
        ContentValues rowValues =new ContentValues();

        rowValues.put(Hike.NAME, hike.getName()); //column name, column value
        rowValues.put(Hike.LOCATION,hike.getLocation());
        rowValues.put(Hike.DATE, hike.getDate());
        rowValues.put(Hike.PARKING, hike.getParking());
        rowValues.put(Hike.LENGTH, hike.getLength());
        rowValues.put(Hike.DIFFICULTY, hike.getDifficulty());
        rowValues.put(Hike.DESCRIPTION, hike.getDescription());
        rowValues.put(Hike.ADDITIONAL1, hike.getAdditional1());
        rowValues.put(Hike.ADDITIONAL2, hike.getAdditional2());
        rowValues.put(Hike.ADDITIONAL_NUM1, hike.getAdditionalNum1());
        rowValues.put(Hike.ADDITIONAL_NUM2, hike.getAdditionalNum2());

        result=database.insertOrThrow(TABLE_HIKE, null,rowValues);
        return result;
    }

    public long updateHike(Hike hike){
        long result = 0;
        ContentValues rowValues =new ContentValues();

        rowValues.put(Hike.NAME, hike.getName()); //column name, column value
        rowValues.put(Hike.LOCATION,hike.getLocation());
        rowValues.put(Hike.DATE, hike.getDate());
        rowValues.put(Hike.PARKING, hike.getParking());
        rowValues.put(Hike.LENGTH, hike.getLength());
        rowValues.put(Hike.DIFFICULTY, hike.getDifficulty());
        rowValues.put(Hike.DESCRIPTION, hike.getDescription());
        rowValues.put(Hike.ADDITIONAL1, hike.getAdditional1());
        rowValues.put(Hike.ADDITIONAL2, hike.getAdditional2());
        rowValues.put(Hike.ADDITIONAL_NUM1, hike.getAdditionalNum1());
        rowValues.put(Hike.ADDITIONAL_NUM2, hike.getAdditionalNum2());

        String where ="id=?";
        String values[]={hike.getId() + ""};
        result=database.update(TABLE_HIKE, rowValues, where, values);

        return result;
    }

    public long deleteHike(int id){
        long result =0;
        String where ="id=?";
        String values[]={String.valueOf(id)};
        result =database.delete(TABLE_HIKE, where, values);
        return result;
    }

    public List<Hike> searchHike(String keyword) throws Exception{
        Cursor cursor = null;
        //SELECT * FROM tblHike WHERE Name LIKE %%
        String query ="SELECT * FROM " + TABLE_HIKE + " WHERE " + Hike.NAME + " LIKE '" + keyword + "%'";
        return searchHike( query, cursor);
    }

    @SuppressLint("SuspiciousIndentation")
    public List<Hike> searchHike(String name, String location, String date, Double length) throws Exception{
        Cursor cursor = null;

        String query ="SELECT * FROM " + TABLE_HIKE
                + " WHERE "
                +  Hike.NAME + "='" + name + "'"
                + " AND " + Hike.LOCATION + "='" + location + "'";
        if (date != null && !date.trim().isEmpty())
            query += " AND " + Hike.DATE + "='" + date + "'";
        if (length !=null )
            query += " AND " + Hike.LENGTH + "='" + length + "'";
            return searchHike( query, cursor);
    }

    public List<Hike>searchHike( String query, Cursor cursor)throws Exception{
    List<Hike> results = new ArrayList<>();
    cursor = database.rawQuery( query, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()){
        Hike hike=new Hike(
                cursor.getInt(0), //id
                cursor.getString(1), //name
                cursor.getString(2), //location
                cursor.getString(3), //date
                cursor.getString(4), //parking
                cursor.getDouble(5), //length
                cursor.getString(6), //difficulty
                cursor.getString(7), //description
                cursor.getString(8), //additional1
                cursor.getString(9), //additional2
                cursor.getDouble(10), //additional num1
                cursor.getDouble(11) //additional num2

        );
        results.add(hike); cursor.moveToNext();

    }
        cursor.close();
        return results;
    }
}







