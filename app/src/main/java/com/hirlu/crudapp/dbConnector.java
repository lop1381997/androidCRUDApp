package com.hirlu.crudapp;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class dbConnector {
    private SQLiteDatabase connection;
    private Context context;
    public dbConnector(Context context, SQLiteDatabase connection) {
        this.context = context;
        this.connection = connection;

//        this.connection.execSQL("DROP TABLE game");
        this.connection.execSQL("CREATE TABLE if not exists game(\n" +
                "  id integer PRIMARY KEY,\n" +
                "  name text,\n" +
                "  year integer,\n" +
                "  description text,\n" +
                "  pegiAge int," +
                "  image int,\n" +
                "  constraint Game UNIQUE (year, name)\n" +
                ");");

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

    public void insert(String name, String description, int year,int pegiAge, int image){

        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("description", description);
        contentValues.put("pegiAge", pegiAge);
        contentValues.put("image", image);

        connection.insert("game", null, contentValues);
    }


    @SuppressLint("Range")
    public Game getGame(int id){
        String query = "SELECT * FROM game where id = "+id;

        Cursor cursor = this.connection.rawQuery(query, null);
        Game game  = null;
        while (cursor.moveToNext()) {
             id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int pegiAge = cursor.getInt(cursor.getColumnIndex("pegiAge"));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
            game = new Game(id, name , year ,description, pegiAge, image);
        }
        return game;

    }

    public List<Game> getData() {
        Cursor cursor = this.connection.rawQuery("SELECT * FROM game", null);
        List<Game> lGames = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int pegiAge = cursor.getInt(cursor.getColumnIndex("pegiAge"));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));


            lGames.add(new Game(id, name, year, description, pegiAge, image));
        }
        return lGames;
    }

    public void update(int id, String name, String description, int year, int pegiAge, int image){

        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("description", description);
        contentValues.put("pegiAge", pegiAge);
        contentValues.put("image", image);
        connection.update("game", contentValues, "id = ?", new String[] { String.valueOf(id) });

    }

    public void delete(int id){
        connection.delete("game", "id = ?", new String[] { String.valueOf(id) })   ;
    }


}
