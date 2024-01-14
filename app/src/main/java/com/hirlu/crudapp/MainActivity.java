package com.hirlu.crudapp;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    private RecyclerView rGames;
    private List<Game> lGames;

    private dbConnector connector;

    private FloatingActionButton addButton;
    private int pos;

    // TODO: 21/10/2023 cambio de paso de recursos a querys de la base de datos
    private void setData(){
        lGames.clear();
        this.connector.insert("Onimusha", 2003,  18, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "Onimusha", 2003, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 18, "R.drawable.ic_launcher_background"));
//        rGames.getAdapter().notifyItemInserted(lGames.size()-1);

        this.connector.insert("Sonic Heroes", 2003, 6, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "Sonic Heroes", 2003, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 6, "R.drawable.ic_launcher_background"));
//        rGames.getAdapter().notifyItemInserted(lGames.size()-1);

        this.connector.insert("Onimusha2", 2003, 18, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "Onimusha2", 2003, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 18, "R.drawable.ic_launcher_background"));
//        rGames.getAdapter().notifyItemInserted(lGames.size()-1);

        this.connector.insert("Zone of the Enders", 2003, 12, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "Zone of the Enders", 2003, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 12, "R.drawable.ic_launcher_background"));
//        rGames.getAdapter().notifyItemInserted(lGames.size()-1);

        this.connector.insert("Onimusha4", 2006, 18, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "Onimusha4", 2006, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 18, "R.drawable.ic_launcher_background"));
//        rGames.getAdapter().notifyItemInserted(lGames.size()-1);

        this.connector.insert("Onimusha3", 2003, 18, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "Onimusha3", 2003, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 18, "R.drawable.ic_launcher_background"));
//        rGames.getAdapter().notifyItemInserted(lGames.size()-1);

        this.connector.insert("DragonBall Budokai Tenkaichi 3", 2003, 12, "R.drawable.ic_launcher_background");
        lGames.add(new Game( "DragonBall Budokai Tenkaichi 3", 2003, "Lorem ipsum dolor sit amet, consectetur adipiscing.", 18, "R.drawable.ic_launcher_background"));
        rGames.getAdapter().notifyDataSetChanged();
    }
    private List<Game> getGamesData(){
        List <Game> list = connector.getData();
        return list;
    }

    @Override
    public void onItemClick(View view, int pos) {
        //swap activity to GameView
        Intent intent = new Intent(view.getContext(), GameView.class);
        intent.setAction(Intent.ACTION_SEND);
        this.lGames = getGamesData();
        if (this.lGames!=null ) {
            intent.putExtra("ID", this.lGames.get(pos).getId());
            intent.putExtra("POS", pos);
        }
        getGamefromActivity.launch(intent);

    }



    @Override
    public void onItemLongClick(int pos) {
        this.pos = pos;
        showDialog( );
    }

    public void showDialog(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.remove_dialog);
        dialog.findViewById(R.id.si).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(pos);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private View.OnClickListener afegir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), GameEditView.class);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("MODE", false);
            getGamefromActivity.launch(intent);
        }
    };

    private final View.OnLongClickListener generateData = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            setData();
            Toast.makeText(MainActivity.this, "generateData", Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    void remove(int pos){
        try {
            if (pos>rGames.getHeight() || pos>lGames.size()){
                throw new Exception("No data found on a the Game");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
        int id = lGames.get(pos).getId();
        connector.delete(id);
//        lGames.remove(pos);
        adapter.removeGame(pos);

    }

    ActivityResultLauncher<Intent> getGamefromActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == RESULT_OK){
                        if (result.getData() != null){
                            String mode= result.getData().getStringExtra("MODE");
                            //delete
                            //true -> delete
                            if (mode.equals("delete")){

                                int pos = result.getData().getIntExtra("POS", 0);
                                remove(pos);
                            } else if (mode.equals("edit")) {
                                int id = result.getData().getIntExtra("ID", 0);
                                int pos = result.getData().getIntExtra("POS", 0);
                                Game game = connector.getGame(id);
                                lGames.remove(pos);
                                lGames.add( pos, game);
//                                rGames.getAdapter().notifyItemInserted(pos);
                                rGames.getAdapter().notifyDataSetChanged();
                            } else if (mode.equals("add")) {

                                int id = result.getData().getIntExtra("ID", 0);
                                Game game = connector.getGame(id);
                                adapter.addGame(game);
                            }
                        }
                    }
                }
            }
    );

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

