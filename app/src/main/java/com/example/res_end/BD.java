package com.example.res_end;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "list.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "sms";
    public static final String  COLUMN_NUMBER = "number"; //номер отправителя или номер получателя
    public static final String COLUMN_TIME = "time";// время отправки
    public static final String COLUMN_MESSAGE = "message"; //содержимое
    public static final String  COLUMN_USER = "user";//отпрвитель или получатель
    public static final String  COLUMN_IND = "ind"; //номер сообщения


    public BD(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String querty = "CREATE TABLE " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NUMBER + " TEXT, "
                + COLUMN_TIME + " BIGINT, "
                + COLUMN_USER + " TEXT, "
                + COLUMN_IND + " INT, "
                + COLUMN_MESSAGE + " BLOB) ;";
        db.execSQL(querty);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
