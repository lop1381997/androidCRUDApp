package com.hirlu.crudapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameEditView extends AppCompatActivity {
    private  int id;
    private int pos;
    private String imageID = "R.drawable.ic_launcher_background";

    private EditText name, year, description, pegiAge;
    private ImageView image;

    private boolean mode;

    private Button guardar;
    private dbConnector connector;

    private View.OnClickListener guardarAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(GameEditView.this, "Guardar/Editar", Toast.LENGTH_SHORT).show();
            Intent intent = null;
            if (mode){
                //edit
                 connector.update(id,
                         name.getText().toString(),
                         description.getText().toString(),
                         Integer.parseInt(year.getText().toString()),
                         Integer.parseInt(pegiAge.getText().toString()),
                         imageID);
                 intent = new Intent(GameEditView.this, MainActivity.class);
                 intent.putExtra("ID", id);
                 intent.putExtra("POS", pos);
                 intent.putExtra("MODE", "edit");
                 setResult(RESULT_OK, intent);

            }
            else{
                //new game
                connector.insert( name.getText().toString(), description.getText().toString(), Integer.parseInt(year.getText().toString()), Integer.parseInt(pegiAge.getText().toString()), imageID);
                List<Game> lGames = connector.getData();
                intent = new Intent(GameEditView.this, MainActivity.class);
                id = lGames.get(lGames.size()-1).getId();
                intent.putExtra("ID", id);
                intent.putExtra("MODE", "add");
                setResult(RESULT_OK, intent);
            }


            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_edit_view);

        SQLiteDatabase db = openOrCreateDatabase("Games", MODE_PRIVATE, null);
        connector = new dbConnector(this, db);

        Intent intent = getIntent();
        mode = intent.getBooleanExtra("MODE", false);


        name = findViewById(R.id.nameEdit);
        year = findViewById(R.id.yearEdit);
        description = findViewById(R.id.descriptionEdit);
        pegiAge = findViewById(R.id.pegiEdit);
        image = findViewById(R.id.imageEdit);

        id = intent.getIntExtra("ID", 0);
        pos = intent.getIntExtra("POS", 0);
        Game game = connector.getGame(id);

        String nameText ;
        String yearText ;
        String descriptionText ;
        String pageAgeText ;

        if (game != null){
            if (game.getName() != "") nameText = game.getName();
            else nameText = " ";

            if ((Integer)game.getYear() != null) yearText = String.valueOf(game.getYear());
            else yearText = " ";

            if (game.getDescription() != null) descriptionText = game.getDescription();
            else descriptionText = " ";

            if ((Integer)game.getPegiAge() != null) pageAgeText = String.valueOf(game.getPegiAge());
            else pageAgeText = " ";

            if ((String)game.getImage() != null) imageID = game.getImage();
            else imageID = "R.drawable.ic_launcher_background";
        }

        else {
            nameText = "";
            yearText = "";
            descriptionText = "";
            pageAgeText = "";
            imageID = "R.drawable.ic_launcher_background";
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

        guardar = findViewById(R.id.guardar);
        guardar.setText(!mode ? "guardar" : "editar");
        guardar.setOnClickListener(guardarAction);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                resultLauncher.launch(intent);
            }
        });

    }
    ActivityResultLauncher<Intent> resultLauncher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == RESULT_OK){
                        if (result.getData() != null){
                            String imageURI = String.valueOf(result.getData().getData());
                            image.setImageURI(Uri.parse(imageURI));

                            imageID = imageURI;
                        }
                    }
                }
            }
    );

}

