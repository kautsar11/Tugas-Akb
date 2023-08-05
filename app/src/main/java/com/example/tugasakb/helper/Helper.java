package com.example.tugasakb.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Helper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "note";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE note (id INTEGER PRIMARY KEY autoincrement, judul TEXT NOT NULL, tanggal TEXT NOT NULL, kategori TEXT NOT NULL, isi TEXT NOT NULL)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS note");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM note ORDER BY id DESC";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("judul", cursor.getString(1));
                map.put("tanggal", cursor.getString(2));
                map.put("kategori", cursor.getString(3));
                map.put("isi", cursor.getString(4));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public HashMap<String, String> getNoteById(int id) {
        HashMap<String, String> note = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("note", new String[]{"id", "judul", "kategori","isi"}, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            if (idIndex != -1) {
                note.put("id", cursor.getString(idIndex));
            }

            int judulIndex = cursor.getColumnIndex("judul");
            if (idIndex != -1) {
                note.put("judul", cursor.getString(judulIndex));
            }

            int kategoriIndex = cursor.getColumnIndex("kategori");
            if (idIndex != -1) {
                note.put("kategori", cursor.getString(kategoriIndex));
            }

            int isiIndex = cursor.getColumnIndex("isi");
            if (idIndex != -1) {
                note.put("isi", cursor.getString(isiIndex));
            }
            cursor.close();
        }
        return note;
    }


    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public void insert(String judul, String kategori, String isi) {
        String tanggal = getCurrentDate();
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "INSERT INTO note (judul, tanggal, kategori, isi) VALUES('" + judul + "', '" + tanggal + "', '" + kategori + "', '" + isi + "')";
        database.execSQL(QUERY);
    }

    public void update(int id, String judul, String kategori, String isi) {
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "UPDATE note SET judul = '" + judul + "', kategori = '" + kategori + "', isi = '" + isi + "' WHERE id = " + id;
        database.execSQL(QUERY);
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM note WHERE id = " + id;
        database.execSQL(QUERY);
    }

}
