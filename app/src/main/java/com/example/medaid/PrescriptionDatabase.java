package com.example.medaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PrescriptionDatabase extends SQLiteOpenHelper {
    SQLiteDatabase db;
    Context context;
    static String DB_Name = "PRESCRIPTION_DATABASE";
    static String TABLE_NAME ="PRESCRIPTION_TABLE";
    static int VERSION = 1;

    public PrescriptionDatabase(Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (_id INTEGER PRIMARY KEY, TITLE STRING, DESCRIPTION STRING);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (VERSION == oldVersion) {
            VERSION = newVersion;
            db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            onCreate(db);
        }
    }

    public void insert(String title, String description) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("DESCRIPTION", description);
        db.insert(TABLE_NAME, null, cv);
    }

    public void update(String id, String title, String description) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("DESCRIPTION", description);
        db.update(TABLE_NAME, cv, "_id=" + id, null);
    }

/*    public void view() {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
        StringBuilder sb = new StringBuilder();
        while (c.moveToNext()) {
            sb.append(c.getString(0) + " " + c.getString(1) + "\n");
        }
        Toast.makeText(ctx, sb.toString(), Toast.LENGTH_LONG).show();
    }*/

/*    public void delete(int id, String title) {
        db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
        int preCount = c.getCount();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE TITLE = \"" + title + "\" OR rowid = \"" + id + "\";");

        c = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
        int postCount = c.getCount();

        if (preCount == postCount) {
            Toast.makeText(ctx, "Title and ID do not exist.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ctx, "Delete successful", Toast.LENGTH_LONG).show();
        }
    }*/

    public Cursor getCursorAll() {
        db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
    }

/*    public Cursor getCursorRange(String min, String max) {
        db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id BETWEEN " + min + " AND " + max + ";", null);
    }*/
}
