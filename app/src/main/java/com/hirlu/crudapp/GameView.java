package com.hirlu.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GameView extends AppCompatActivity {

    private boolean menu_clicked = false;


    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation frombottom;
    private Animation tobottom;

    private  FloatingActionButton menu_button;
    private  FloatingActionButton add_button;
    private  FloatingActionButton delete_button;

    private dbConnector connector;


    private final View.OnClickListener menu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onMenuClicked();
            Toast.makeText(GameView.this, "menu", Toast.LENGTH_SHORT).show();
        }
    };
    private final View.OnClickListener add = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onAddClick();
            Toast.makeText(GameView.this, "edit", Toast.LENGTH_SHORT).show();
        }
    };




    private final View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(GameView.this, "delete", Toast.LENGTH_SHORT).show();
            connector.delete(id);
            finish();

        }
    };

    private void onAddClick(){
        Intent intent = new Intent(this, GameEditView.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("MODE", true);
        intent.putExtra("ID", id);
//        intent.putExtra("NAME", name.getText());
//        intent.putExtra("YEAR", year.getText());
//        intent.putExtra("DESCRIPTION", description.getText());
//        intent.putExtra("PEGIAGE", pegiAge.getText());
//        intent.putExtra("IMAGE", imageID);
        startActivity(intent);

    }

    private void onMenuClicked(){
        setVisibility();
        setClickable();
        setAnimation();
        menu_clicked = !menu_clicked;
    }

    private void setVisibility(){
        if (!this.menu_clicked){
            add_button.setVisibility(View.VISIBLE);
            delete_button.setVisibility(View.VISIBLE);
        }
        else {
            add_button.setVisibility(View.INVISIBLE);
            delete_button.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(){
        if (!this.menu_clicked) {
            add_button.startAnimation(frombottom);
            delete_button.startAnimation(frombottom);
            menu_button.startAnimation(rotateOpen);
        }
        else {
            add_button.startAnimation(tobottom);
            delete_button.startAnimation(tobottom);
            menu_button.startAnimation(rotateClose);
        }
    }

    private void setClickable(){
        if(this.menu_clicked){
            add_button.setClickable(false);
            delete_button.setClickable(false);
        }
        else {
            add_button.setClickable(true);
            delete_button.setClickable(true);
        }
    }

    private TextView name, year, description, pegiAge;

    private int id;

    private ImageView image;
    private int imageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        SQLiteDatabase db = openOrCreateDatabase("Games", MODE_PRIVATE, null);
        connector = new dbConnector(this, db);

        menu_button = findViewById(R.id.menuButton);
        add_button = findViewById(R.id.addButton);
        delete_button = findViewById(R.id.deleteButton);
        //load animations
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        tobottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        //setting on click listener
        menu_button.setOnClickListener(menu);
        add_button.setOnClickListener(add);
        delete_button.setOnClickListener(delete);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
//        String nameText = intent.getStringExtra("NAME");
//        String yearText = String.valueOf(intent.getIntExtra("YEAR", 0));
//        String descriptionText = intent.getStringExtra("DESCRIPTION");
//        String pageAgeText = String.valueOf(intent.getIntExtra("PEGIAGE", 0));
//        imageID = intent.getIntExtra("IMAGE", R.drawable.ic_launcher_background);

        Game game = connector.getGame(id);

        String nameText = game.getName();
        String yearText = String.valueOf(game.getYear());
        String descriptionText = game.getDescription();
        String pageAgeText = String.valueOf(game.getPegiAge());
        imageID = game.getImage();

        name = findViewById(R.id.nameEdit);
        year = findViewById(R.id.yearEdit);
        description = findViewById(R.id.descriptionEdit);
        pegiAge = findViewById(R.id.pegiEdit);
        image = findViewById(R.id.imageEdit);
        name.setText(nameText);
        year.setText(yearText);
        description.setText(descriptionText);
        pegiAge.setText(pageAgeText);
        image.setImageResource(imageID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //send necessary data


    }
}
