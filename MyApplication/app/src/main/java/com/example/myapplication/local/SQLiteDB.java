package com.example.myapplication.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDB {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "SQLiteDB";

    private static final String DATABASE_TABLE = "sample";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table user (id int primary key,email text,image text, nom text,password text, prenom text,role text,telephone text);" ;
    private static final String DATABASE_CREATE1 = "create table classe (id int primary key,filiere text,niveau text,nom text);";
    private static final String DATABASE_CREATE2 = "create table user_classes (user_id int references user(id),classe_id int references classe(id));";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public SQLiteDB(Context ctx) {

        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS classe");
            db.execSQL("DROP TABLE IF EXISTS user_classes");
            onCreate(db);
        }
    }

    //---open SQLite DB---
    public SQLiteDB open() throws SQLException {

        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---close SQLite DB---
    public void close() {

        DBHelper.close();
    }

    //---insert data into SQLite DB---
    public long insert(String id, String name) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, id);
        initialValues.put(KEY_NAME, name);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---Delete All Data from table in SQLite DB---
    public void deleteAll() {

        db.delete(DATABASE_TABLE, null, null);
    }
}
