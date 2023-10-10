package com.hirlu.crudapp;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class dbConnector {
    private SQLiteDatabase connection;
    private Context context;
    public dbConnector(Context context, SQLiteDatabase connection) {
        this.context = context;
        this.connection = connection;

        this.connection.execSQL("CREATE TABLE if not exists game(\n" +
                "  id integer PRIMARY KEY,\n" +
                "  name text,\n" +
                "  year integer,\n" +
                "  description text,\n" +
                "  pegiAge int," +
                "  image int,\n" +
                "  constraint Game UNIQUE (year, name)\n" +
                ");");


//        this.connection.execSQL("DROP TABLE game");



    }
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing.";
    public void insert(String name, int year,int pegiAge, int image){

        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("description", lorem);
        contentValues.put("pegiAge", pegiAge);
        contentValues.put("image", image);

        connection.insert("game", null, contentValues);
    }


    public List<Game> getData() {
        Cursor cursor = this.connection.rawQuery("SELECT * FROM game", null);
        List<Game> lGames = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int pegiAge = cursor.getInt(cursor.getColumnIndex("pegiAge"));
            int image = cursor.getInt(cursor.getColumnIndex("image"));


            lGames.add(new Game(name, year, description, pegiAge, image));
        }
    return lGames;
    }
}
