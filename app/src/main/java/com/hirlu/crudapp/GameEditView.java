package com.hirlu.crudapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameEditView extends AppCompatActivity {
    private  int id;
    private int pos;
    private String imageID = "R.drawable.ic_launcher_background";
    private byte[] byteArray;

    private EditText name, year, description, pegiAge;
    private ImageView imageView;

    private boolean mode;

    private Button guardar;
    private dbConnector connector;

    private Button enrere;
    private final View.OnClickListener enrereAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener guardarAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(GameEditView.this, "Guardar/Editar", Toast.LENGTH_SHORT).show();
            Intent intent = null;
            if (mode){
                //edit game
                byte[] byteTabla = null;
                if (imageView.getDrawable() instanceof VectorDrawable){
                    byteTabla = null;
                }
                 else {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                     byteTabla = byteArrayOutputStream.toByteArray();
                }
                    connector.update(id,
                            name.getText().toString(),
                            description.getText().toString(),
                            Integer.parseInt(year.getText().toString()),
                            Integer.parseInt(pegiAge.getText().toString()),
                            byteTabla);
                 intent = new Intent(GameEditView.this, MainActivity.class);
                 intent.putExtra("ID", id);
                 intent.putExtra("POS", pos);
                 intent.putExtra("MODE", "edit");
                 setResult(RESULT_OK, intent);

            }
            else{
                //new game
                byte[] byteTabla = null;
                if (imageView.getDrawable() instanceof VectorDrawable){
                    byteTabla = null;
                }
                else {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteTabla = byteArrayOutputStream.toByteArray();
                }
                connector.insert( name.getText().toString(), description.getText().toString(), Integer.parseInt(year.getText().toString()), Integer.parseInt(pegiAge.getText().toString()), byteTabla);
                List<Game> lGames = connector.getDataWithImage();
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
        imageView = findViewById(R.id.imageEdit);

        id = intent.getIntExtra("ID", 0);
        pos = intent.getIntExtra("POS", 0);
        Game game = connector.getGameWithImage(id);

        enrere = findViewById(R.id.enrere);
        enrere.setOnClickListener(enrereAction);
        enrere.setText("Enrere");

        String nameText ;
        String yearText ;
        String descriptionText ;
        String pageAgeText ;

        if (game != null){
            if (game.getName() != "") nameText = game.getName();
            else nameText = "Name";

            if ((Integer)game.getYear() != null) yearText = String.valueOf(game.getYear());
            else yearText = "Year";

            if (game.getDescription() != null) descriptionText = game.getDescription();
            else descriptionText = "Description";

            if ((Integer)game.getPegiAge() != null) pageAgeText = String.valueOf(game.getPegiAge());
            else pageAgeText = "PegiAge";

            if ((String)game.getImage() != null) imageID = game.getImage();
            else imageID = "R.drawable.ic_launcher_background";

            if (game.getImageByte() != null) byteArray = game.getImageByte();
            else byteArray = null;
        }

        else {
            nameText = "";
            yearText = "";
            descriptionText = "";
            pageAgeText = "";
            imageID = "R.drawable.ic_launcher_background";
        }



        name.setText(nameText);
        year.setText(yearText);
        description.setText(descriptionText);
        pegiAge.setText(pageAgeText);

        if (byteArray == null) imageView.setImageResource(R.drawable.ic_launcher_background);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        if (bitmap == null) imageView.setImageResource(R.drawable.ic_launcher_background);
        else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bitmap);
        }

        if (imageID.equals("R.drawable.ic_launcher_background")){
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        else imageView.setImageURI(Uri.parse(imageID));

        guardar = findViewById(R.id.guardar);
        guardar.setText(!mode ? "guardar" : "editar");
        guardar.setOnClickListener(guardarAction);

        imageView.setOnClickListener(new View.OnClickListener() {
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
//                            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(imageURI));
//                            image.setImageURI(contentUri);
                            imageView.setImageURI(Uri.parse(imageURI));
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imageURI));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                             byteArray = byteArrayOutputStream.toByteArray();
                            imageID = imageURI;
                        }
                    }
                }
            }
    );

}

