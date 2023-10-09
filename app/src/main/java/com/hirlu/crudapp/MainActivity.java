package com.hirlu.crudapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rGames;
    private List<Game> lGames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rGames = (RecyclerView) findViewById(R.id.rv);
        lGames = getGamesData();

        GameRecyclerAdapter adapter = new GameRecyclerAdapter(this, lGames);
        rGames.setAdapter(adapter);
        rGames.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Game> getGamesData(){
        List <Game> list = new ArrayList<>();
        list.add(new Game("Onimusha", 2003, "lorem", 18, R.drawable.ic_launcher_background));
        list.add(new Game("Sonic Heroes", 2003, "lorem", 6, R.drawable.ic_launcher_background));
        list.add(new Game("Onimusha2", 2003, "lorem", 18, R.drawable.ic_launcher_background));
        list.add(new Game("Zone of the Enders", 2003, "lorem", 12, R.drawable.ic_launcher_background));
        list.add(new Game("Onimusha", 2003, "lorem", 12, R.drawable.ic_launcher_background));
        list.add(new Game("Onimusha3", 2003, "lorem", 18, R.drawable.ic_launcher_background));
        list.add(new Game("DragonBall Budokai Tenkaichi 3", 2003, "lorem", 12, R.drawable.ic_launcher_background));

        return list;
    }
}