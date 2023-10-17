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


    private static final String CREATE_OBSERVATION_TABLE =String.format(
            "CREATE TABLE IF NOT EXIST %s("+ //tblObservation
                    " %s INTEGER PRIMARY KEY AUTOINCREMENT," + //id
                    " %s INTEGER,"+ //hike_id
                    " %s TEXT,"+ //observation
                    " %s BIGINT,"+ //date_time
                    " %s TEXT,"+ //comment
                    " %s TEXT,"+ //str1
                    " %s TEXT,"+ //str2
                    " %s TEXT,"+ //d1
                    " %s TEXT,"+ //d2
                    " CONSTRAINT fk_hike FOREIGN KEY (%s) REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE CASCADE)"
            , TABLE_OBSERVATION,Observation.ID, Observation.HIKE_ID, Observation.OBSERVATION,Observation.DATE_TIME, Observation.COMMENT,
            Observation.STR1, Observation.STR2, Observation.D1, Observation.D2, Observation.HIKE_ID, TABLE_HIKE, Hike.ID);


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
        sqLiteDatabase.execSQL(CREATE_OBSERVATION_TABLE);

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
            query += " AND " + Hike.LENGTH + "='" + length;
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


    public long saveObservation(Observation observation){
        long result = 0;
        ContentValues rowValues =new ContentValues();

        rowValues.put(Observation.HIKE_ID, observation.getHikeId()); //column name, column value
        rowValues.put(Observation.OBSERVATION,observation.getObservation());
        rowValues.put(Observation.DATE_TIME, observation.dateTimeToSeconds());
        rowValues.put(Observation.COMMENT, observation.getComment());
        rowValues.put(Observation.STR1, observation.getStr1());
        rowValues.put(Observation.STR2, observation.getStr2());
        rowValues.put(Observation.D1, observation.getD1());
        rowValues.put(Observation.D2, observation.getD2());
        result=database.insertOrThrow(TABLE_OBSERVATION, null,rowValues);
        return result;
    }

    public long updateObservation(Observation observation){
        long result = 0;
        ContentValues rowValues =new ContentValues();

        rowValues.put(Observation.OBSERVATION,observation.getObservation());
        rowValues.put(Observation.DATE_TIME, observation.dateTimeToSeconds());
        rowValues.put(Observation.COMMENT, observation.getComment());
        rowValues.put(Observation.STR1, observation.getStr1());
        rowValues.put(Observation.STR2, observation.getStr2());
        rowValues.put(Observation.D1, observation.getD1());
        rowValues.put(Observation.D2, observation.getD2());
        result=database.insertOrThrow(TABLE_OBSERVATION, null,rowValues);

        String where ="id=?";
        String values[]={observation.getId() + ""};
        result=database.update(TABLE_OBSERVATION, rowValues, where, values);

        return result;
    }

    public long deleteObservation(int id){
        long result =0;
        String where ="id=?";
        String values[]={String.valueOf(id)};
        result =database.delete(TABLE_OBSERVATION, where, values);
        return result;
    }

    public List<Observation> searchObservation(Integer hikeId) throws Exception{
        Cursor cursor = null;
        //SELECT * FROM tblObservation WHERE hikeId =
        String query ="SELECT * FROM " + TABLE_OBSERVATION + " WHERE " + Observation.HIKE_ID + "=" + hikeId;
        return searchObservation( query, cursor);
    }

    public List<Observation>searchObservation( String query, Cursor cursor)throws Exception{
        List<Observation> results = new ArrayList<>();
        cursor = database.rawQuery( query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Observation observation=new Observation(
                    cursor.getInt(0), //id
                    cursor.getInt(1), //hike_id
                    cursor.getString(2), //observation
                    Observation.secondToDateTime(cursor.getLong(3)), //datetime
                    cursor.getString(4), //comment
                    cursor.getString(5), //str1
                    cursor.getString(6), //str2
                    cursor.getDouble(7), //d1
                    cursor.getDouble(8) //d2

            );
            results.add(observation); cursor.moveToNext();

        }
        cursor.close();
        return results;
    }
}







