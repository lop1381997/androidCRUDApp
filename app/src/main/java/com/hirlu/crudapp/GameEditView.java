package com.hirlu.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameEditView extends AppCompatActivity {
    private  int id, imageID = 0;

    private EditText name, year, description, pegiAge;
    private ImageView image;

    private boolean mode;

    private Button guardar;
    private dbConnector connector;

    private View.OnClickListener guardarAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(GameEditView.this, "Guardar/Editar", Toast.LENGTH_SHORT).show();
            if (mode){
                 connector.update(id,
                         name.getText().toString(),
                         description.getText().toString(),
                         Integer.parseInt(year.getText().toString()),
                         Integer.parseInt(pegiAge.getText().toString()),
                         imageID);
            }
            else{
                connector.insert( name.getText().toString(), description.getText().toString(), Integer.parseInt(year.getText().toString()), Integer.parseInt(pegiAge.getText().toString()), imageID);
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
        name.setText(intent.getStringExtra("NAME"));
        year.setText(intent.getStringExtra("YEAR"));
        description.setText(intent.getStringExtra("DESCRIPTION"));
        pegiAge.setText(intent.getStringExtra("PEGIAGE"));
        imageID = intent.getIntExtra("IMAGE",  R.drawable.ic_launcher_background);
        image.setImageResource(imageID);

        guardar = findViewById(R.id.guardar);
        guardar.setText(!mode ? "guardar" : "editar");
        guardar.setOnClickListener(guardarAction);

    }
}