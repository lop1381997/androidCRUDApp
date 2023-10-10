package com.hirlu.crudapp;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rGames;
    private List<Game> lGames;

    private dbConnector connector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = openOrCreateDatabase("Games", MODE_PRIVATE, null);
        connector = new dbConnector(this, db);
//        setData();

        rGames = (RecyclerView) findViewById(R.id.rv);
        lGames = getGamesData();

        GameRecyclerAdapter adapter = new GameRecyclerAdapter(this, lGames);
        rGames.setAdapter(adapter);
        rGames.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setData(){
        this.connector.insert("Onimusha", 2003,  18, R.drawable.ic_launcher_background);
        this.connector.insert("Sonic Heroes", 2003, 6, R.drawable.ic_launcher_background);
        this.connector.insert("Onimusha", 2003, 18, R.drawable.ic_launcher_background);
        this.connector.insert("Onimusha2", 2003, 18, R.drawable.ic_launcher_background);
        this.connector.insert("Zone of the Enders", 2003, 12, R.drawable.ic_launcher_background);
        this.connector.insert("Onimusha4", 2006, 12, R.drawable.ic_launcher_background);
        this.connector.insert("Onimusha3", 2003, 18, R.drawable.ic_launcher_background);
        this.connector.insert("DragonBall Budokai Tenkaichi 3", 2003, 12, R.drawable.ic_launcher_background);
    }
    private List<Game> getGamesData(){
        List <Game> list = connector.getData();
        return list;
    }
}

//TODO: LONG CLICK on buttons make toast apear with action name