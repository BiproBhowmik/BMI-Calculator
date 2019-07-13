package com.joy.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_RESULT = "Result";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_STAGE = "Stage";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_HIGHT = "Hight";
    private static final String KEY_DATE = "Date";
    DatePicker picker;

    //private static final String SelectAll = "Select * From "+ TABLE_CONTACTS;

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Toast.makeText(context, "OnCreate ", Toast.LENGTH_LONG).show();
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT, "+KEY_GENDER+" TEXT, "+KEY_WEIGHT+" TEXT, "+KEY_HIGHT+" TEXT, "+KEY_RESULT+" DOUBLE, "+KEY_STAGE+" TEXT, "+KEY_DATE+" TEXT ) ";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Toast.makeText(context, "OnUpgrate ", Toast.LENGTH_LONG).show();
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);  ///upgrade version number***
    }

    // code to add the new contact
    void addData(String name, Double result, String gender, String stage, String weight, String ft, String inch, String date) {
        //Toast.makeText(context, "Inserted ", Toast.LENGTH_LONG).show();
        deleteData(name);
        SQLiteDatabase db = this.getWritableDatabase();
        //LocalDate date = LocalDate.now();

        String hight = ft + " Feet " + inch + " Inch";

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Contact Name
        values.put(KEY_GENDER, gender); // Contact Gender
        values.put(KEY_WEIGHT, weight + " kg"); // Contact Weight
        values.put(KEY_HIGHT, hight); // Contact Hight
        values.put(KEY_RESULT, result); // Contact Phone
        values.put(KEY_STAGE, stage); // Contact Stage
        values.put(KEY_DATE, date); // Contact Date

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Deleting single contact
    public void deleteData(String name) {
        //Toast.makeText(context, "Deleted ", Toast.LENGTH_LONG).show();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
        db.close();
    }

    public Cursor displayAllData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * From "+ TABLE_CONTACTS, null);

        return cursor;
    }


}
