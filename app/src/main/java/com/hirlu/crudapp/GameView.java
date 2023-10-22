package com.hirlu.crudapp;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

    private void onAddClick(){
        Intent intent = new Intent(this, GameEditView.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("MODE", true);
        intent.putExtra("ID", id);


            getGamefromActivity.launch(intent);

    }

    ActivityResultLauncher<Intent> getGamefromActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == RESULT_OK){
                        if (result.getData() != null){
                            Intent intent = getIntent();
                            id = intent.getIntExtra("ID", 0);
                            pos = intent.getIntExtra("POS", 0);

                            Game game = connector.getGame(id);

                            String nameText = null;
                            String yearText = null;
                            String descriptionText = null;
                            String pageAgeText = null;
                            imageID = "R.drawable.ic_launcher_background";

                            if (game != null){
                                nameText = game.getName();
                                yearText = String.valueOf(game.getYear());
                                descriptionText = game.getDescription();
                                pageAgeText = String.valueOf(game.getPegiAge());
                                imageID = game.getImage();
                            }
                            name.setText(nameText);
                            year.setText(yearText);
                            description.setText(descriptionText);
                            pegiAge.setText(pageAgeText);
                            if (imageID.equals("R.drawable.ic_launcher_background")){
                                image.setImageResource(R.drawable.ic_launcher_background);
                            }
//                            else image.setImageURI(Uri.parse(imageID));

                        }
                    }
                }
            }
    );


    private final View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog();
        }
    };

    void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.remove_dialog);
        dialog.findViewById(R.id.si).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(GameView.this, "delete", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GameView.this, MainActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("POS", pos);
                intent.putExtra("MODE","delete" );
                setResult(RESULT_OK, intent);
                finish();
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

    private int id, pos;

    private ImageView image;
    private String imageID;

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
        pos = intent.getIntExtra("POS", 0);

        Game game = connector.getGame(id);

        String nameText = null;
        String yearText = null;
        String descriptionText = null;
        String pageAgeText = null;
        imageID = "R.drawable.ic_launcher_background";

        if (game != null){
            nameText = game.getName();
            yearText = String.valueOf(game.getYear());
            descriptionText = game.getDescription();
            pageAgeText = String.valueOf(game.getPegiAge());
            imageID = game.getImage();
        }



        name = findViewById(R.id.nameEdit);
        year = findViewById(R.id.yearEdit);
        description = findViewById(R.id.descriptionEdit);
        pegiAge = findViewById(R.id.pegiEdit);
        image = findViewById(R.id.imageEdit);
        name.setText(nameText);
        year.setText(yearText);
        description.setText(descriptionText);
        pegiAge.setText(pageAgeText);
        if (imageID.equals("R.drawable.ic_launcher_background")){
            image.setImageResource(R.drawable.ic_launcher_background);
        }
//        else image.setImageURI(Uri.parse(imageID));

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Intent intent =  new Intent(GameView.this, MainActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("POS", pos);
                intent.putExtra("MODE", "edit");
                setResult(RESULT_OK, intent);
                GameView.super.onBackPressed();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }






}
