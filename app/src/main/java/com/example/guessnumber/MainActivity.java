package com.example.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    public void getNameByDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Añadir usuario al ranking");
        alert.setMessage("Introduzca su usuario:");

        final EditText userName = new EditText(this);
        alert.setView(userName);

        alert.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean match = false;
                name = userName.getText().toString();
                Intent intent = new Intent(MainActivity.this, Ranking.class);
                if (!ranking.isEmpty()) {
                    for (User bRanking : ranking) {
                        if (bRanking.getName().equalsIgnoreCase(name)) {
                            bRanking.setAttempts(tries);
                            match = true;
                            break;
                        }
                    }
                }
                if (!match) {
                    ranking.add(new User(name, tries));
                }
                Log.v("DEBUG", "antes del ranking");
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close
            }
        });
        alert.show();
    }

}