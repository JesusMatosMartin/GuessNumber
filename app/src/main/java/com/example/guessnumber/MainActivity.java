package com.example.guessnumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //Variables
    private int numberToGuess = (int) (Math.random() * 100 + 1);
    private int tries = 0;
    private boolean guess = false;
    private int max = 100;
    private int min = 0;
    public static ArrayList<User> ranking = new ArrayList<>();
    private String name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "Importacion" del layout
        final EditText et = findViewById(R.id.editTextNumber);
        final TextView tv = findViewById(R.id.attempts);
        final Button bCkeck = findViewById(R.id.button);
        final Button bRanking = findViewById(R.id.rankingActivityOpener);

        //Se inicializa el texto con el numero de "tries" en ek TextView de attempts
        tv.setText(("Attempts: " + tries));

        bCkeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 0;
                try {
                    if (et.getText() != null) {
                        x = Integer.parseInt(String.valueOf(et.getText()));
                        tries++;
                    } else {
                        Toast.makeText(MainActivity.this, "Tienes que insertar un numero!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // Pass
                }

                if (bCkeck.getText().equals("cleared")) {
                    numberToGuess = (int) (Math.random() * 100 + 1);
                    bCkeck.setText(R.string.check);
                    tries = 0;
                    tv.setText(("Intentos: " + tries));
                } else {
                    tv.setText(("Intentos: " + tries));
                    if (numberToGuess == x) {
                        bCkeck.setText(R.string.clear);
                        Toast.makeText(MainActivity.this, "Felicidades has encontrado el numero " + numberToGuess, Toast.LENGTH_LONG).show();
                        guess = true;
                    } else if (numberToGuess < x) {
                        Toast.makeText(MainActivity.this, "El numero es mas pequeño que " + x, Toast.LENGTH_SHORT).show();
                        if (x < max) {
                            max = x;
                        }
                        et.setHint("Numero entre " + min + " y " + max);
                    } else if (numberToGuess > x) {
                        Toast.makeText(MainActivity.this, "El numero es mas grande que " + x, Toast.LENGTH_SHORT).show();
                        if (x > min) {
                            min = x;
                        }
                        et.setHint("Numero entre " + min + " y " + max);
                    }
                    et.setText("");
                }
            }
        });

        bRanking.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (guess) {
                    getNameByDialog();
                } else {
                    if (!(ranking.size() == 0)) {
                        Intent intent = new Intent(MainActivity.this, Ranking.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    //////////////////////////////////////////////////
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, Ranking.class);
            ranking.add(new User(name, tries, getLatestPhoto()));
            startActivity(intent);
        }
    }
    private Uri getLatestPhoto() {
        File f = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (f.exists()) {
            if (f.listFiles() != null) {
                return FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", f.listFiles()[f.listFiles().length - 1]);
            } else {
                return null;
            }
        }
        return null;
    }
    ////////////////////////////////////////////////

    public void getNameByDialog() {
        final EditText userName = new EditText(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Añadir usuario al ranking");
        alert.setMessage("Introduzca su usuario:");
        alert.setView(userName);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean match = false;
                name = userName.getText().toString();
                Log.v("DEBUG", "Name is: " + name);
                if (name.isEmpty()) {
                    name = "player";
                }
                Log.v("DEBUG", "New name is: " + name);
                if (!ranking.isEmpty()) {
                    for (User r : ranking) {
                        if (r.getName().equalsIgnoreCase(name)) {
                            r.setAttempts(tries);
                            match = true;
                            break;
                        }
                    }
                }
                if (!match) {
                    dispatchTakePictureIntent();
                }

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close
            }
        });
        alert.show();
    }

}