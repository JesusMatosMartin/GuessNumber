package com.example.guessnumber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ranking extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_layout);

        final Button b = findViewById(R.id.backButton);
        final ListView lv = findViewById(R.id.rankingList);

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, R.layout.ranking_layout, MainActivity.ranking) {
            @Override
            public View getView(int pos, View cv, ViewGroup container) {
                Log.v("DEBUG", "Ranking");
                if (cv == null) {
                    cv = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                ((TextView) cv.findViewById(R.id.name)).setText(getItem(pos).getName());
                ((TextView) cv.findViewById(R.id.attempts)).setText("Intentos: " + String.valueOf(getItem(pos).getAttempts()));
                ((ImageView) cv.findViewById(R.id.iv_avatar)).setImageURI(getItem(pos).getFileUri());
                return cv;
            }
        };

        lv.setAdapter(adapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ranking.this, MainActivity.class));
            }
        });
    }
}
