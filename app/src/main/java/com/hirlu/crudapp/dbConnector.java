package com.hirlu.crudapp;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
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
                "  imageByte BLOB,\n" +
                "  constraint Game UNIQUE (year, name)\n" +
                ");");

    }
    private String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing.";

    public void insert(String name,String desc, int year,int pegiAge, byte[] imageByte){

        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("description", desc);
        contentValues.put("pegiAge", pegiAge);
        contentValues.put("imageByte", imageByte);

        connection.insert("game", null, contentValues);
    }
    public String getLorem(){
        return lorem;
    }

     @SuppressLint("Range")
    public Game getGame(int id){
        String query = "SELECT * FROM game where id = "+id;
        Cursor cursor = null;

        try {
            cursor = this.connection.rawQuery(query, null);
            if (cursor.getCount()< -1){
                throw new Exception("No data found");
            }
        } catch (Exception e) {
            Log.e("dbConnection", "Error selecting data into database.", e);
        }

        Game game  = null;
        if (cursor.moveToFirst()) {
             id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int pegiAge = cursor.getInt(cursor.getColumnIndex("pegiAge"));
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("imageByte"));
            game = new Game(id, name , year ,description, pegiAge, image);
        }
        return game;

    }

    @SuppressLint("Range")
    public Game getGameWithImage(int id){
        String query = "SELECT * FROM game where id = "+id;
        Cursor cursor = null;

        try {
            cursor = this.connection.rawQuery(query, null);
            if (cursor.getCount()< -1){
                throw new Exception("No data found");
            }
        } catch (Exception e) {
            Log.e("dbConnection", "Error selecting data into database.", e);
        }

        Game game  = null;
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int pegiAge = cursor.getInt(cursor.getColumnIndex("pegiAge"));
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("imageByte"));
//            byte[] imagebyte = null;
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
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));


            lGames.add(new Game(id, name, year, description, pegiAge, image));
        }
        return lGames;
    }

    public List<Game> getDataWithImage() {
        Cursor cursor = this.connection.rawQuery("SELECT * FROM game", null);
        List<Game> lGames = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int pegiAge = cursor.getInt(cursor.getColumnIndex("pegiAge"));
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("imageByte"));

            lGames.add(new Game(id, name, year, description, pegiAge, image));
        }
        cursor.close();
        return lGames;
    }


    public void update(int id, String name, String description, int year, int pegiAge, byte[] image){

        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("description", description);
        contentValues.put("pegiAge", pegiAge);
        contentValues.put("imageByte", image);
        connection.update("game", contentValues, "id = ?", new String[] { String.valueOf(id) });

    }
    public void delete(int id){
        connection.delete("game", "id = ?", new String[] { String.valueOf(id) })   ;
    }


}
