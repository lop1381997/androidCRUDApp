package com.hirlu.crudapp;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    private RecyclerView rGames;
    private List<Game> lGames;

    private dbConnector connector;

    private FloatingActionButton addButton;
 // TODO: 21/10/2023 cambio de paso de recursos a querys de la base de datos
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

    //regenero la lista

    //recyccler.adapter.noty



    @Override
    public void onItemClick(View view, int pos) {
        //swap activity to GameView
        Intent intent = new Intent(view.getContext(), GameView.class);
        intent.setAction(Intent.ACTION_SEND);
        this.lGames = getGamesData();
        if (this.lGames!=null) {
            intent.putExtra("ID", this.lGames.get(pos).getId());
//            intent.putExtra("NAME", this.lGames.get(pos).getName());
//            intent.putExtra("YEAR", this.lGames.get(pos).getYear());
//            intent.putExtra("DESCRIPTION", this.lGames.get(pos).getDescription());
//            intent.putExtra("PEGIAGE", this.lGames.get(pos).getPegiAge());
//            intent.putExtra("IMAGE", this.lGames.get(pos).getImage());
        }
        startActivity(intent);

    }



    @Override
    public void onItemLongClick(int pos) {
        Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
        int id = lGames.get(pos).getId();
        lGames.remove(pos);
        connector.delete(id);
        lGames = getGamesData();
        adapter.notifyItemRemoved(pos);

    }

    private View.OnClickListener afegir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), GameEditView.class);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("MODE", false);
            intent.putExtra("NAME", "");
            intent.putExtra("YEAR", "");
            intent.putExtra("DESCRIPTION", "");
            intent.putExtra("PEGIAGE", "");
            intent.putExtra("IMAGE",  R.drawable.ic_launcher_background);
            startActivity(intent);
        }
    };

    private final View.OnLongClickListener generateData = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            setData();
            return true;
        }
    };

    private GameRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = openOrCreateDatabase("Games", MODE_PRIVATE, null);
        connector = new dbConnector(this, db);
//        setData();

        rGames = (RecyclerView) findViewById(R.id.rv);

        addButton = findViewById(R.id.afegir);
        addButton.setOnClickListener(afegir);
        addButton.setOnLongClickListener(generateData);

        lGames = getGamesData();
        adapter = new GameRecyclerAdapter(this, lGames, this);
        rGames.setHasFixedSize(false);
        rGames.setAdapter(adapter);
        rGames.setLayoutManager(new LinearLayoutManager(this));
    }
}

