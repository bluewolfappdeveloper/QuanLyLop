package com.tiger.quanlylop.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class DataProvider extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbQuanLyLop.db";
    private static final int DATABASE_VERSION = 1;
    private String[] DATABASE_TABLES = {"Users", "Class", "Student", "Fee", "PayFee"};
    private String[] DATABASE_FIELDS = {
            " ID  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  DISPLAYNAME  TEXT NOT NULL,  USERNAME  TEXT NOT NULL,  PASSWORD  TEXT NOT NULL ",
            " ID  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  NAMECLASS  TEXT NOT NULL,  FEECLASS  TEXT ",
            " ID  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  IDCLASS  INTEGER NOT NULL,  NAMESTUDENT  TEXT NOT NULL,  PHONESTUDENT  TEXT, FOREIGN KEY( IDCLASS ) REFERENCES  Class ( ID ) ",
            " ID  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  NAMEFEE  TEXT NOT NULL,  PRICEFEE  TEXT NOT NULL,  SHOW  INT NOT NULL ",
            " ID  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  IDCLASS  INTEGER NOT NULL,  IDSTUDENT  INTEGER NOT NULL,  IDFEE  INTEGER NOT NULL,  DATE  DATETIME NOT NULL "
    };
    private Context context;

    private static DataProvider instance;

    public static DataProvider getInstance(@Nullable Context context) {
        if (instance == null) instance = new DataProvider(context);
        context = context;
        return instance;
    }

    private DataProvider(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < DATABASE_TABLES.length; i++) {
            String queryCreateTable = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLES[i] + " (" + DATABASE_FIELDS[i] + ")";
            db.execSQL(queryCreateTable);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xóa tất cả các bảng
        for (int i = 0; i < DATABASE_TABLES.length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLES[i]);
        }
        //Tiến hành tạo bảng mới
        onCreate(db);
    }

    public Cursor excuteQuery(String query, String[] params) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, params);
        return cursor;
    }

    public long insertObject(String nameTable, ContentValues addRow) {
        SQLiteDatabase db = getReadableDatabase();
        return db.insert(nameTable, null, addRow);
    }

    public int deleteObject(String nameTable, String conditionDel, String[] params) {
        //db.delete("nameTable","id=? and name=?",new String[]{"1","jack"});
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(nameTable, conditionDel, params);
    }

    public int updateObject(String nameTable, ContentValues updateRow, String conditionDel, String[] params) {
        /*
            ContentValues cv = new ContentValues();
            cv.put("Field1","Bob"); //These Fields should be your String values of actual column names
            cv.put("Field2","19");
            cv.put("Field2","Male");

            myDB.update(nameTable, cv, "id = ?", new String[]{id});
        */

        SQLiteDatabase db = getReadableDatabase();
        return db.update(nameTable, updateRow, conditionDel, params);
    }

    public void deleteFormatTable(String nameTable) {
        deleteObject(nameTable,null, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("seq","0");
        updateObject("SQLITE_SEQUENCE",contentValues, "name = ?", new String[]{nameTable});
    }

}