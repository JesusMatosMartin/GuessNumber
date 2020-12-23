package com.example.guessnumber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ranking extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_layout);

        final Button b = findViewById(R.id.backButton);
        final ListView lv = findViewById(R.id.rankingList);

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, R.layout.ranking_items, MainActivity.ranking) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                Log.v("DEBUG", "Ranking");
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.ranking_items, container, false);
                }
                ((TextView) convertView.findViewById(R.id.name)).setText(getItem(pos).getName());
                ((TextView) convertView.findViewById(R.id.attempts)).setText("Intentos: " + String.valueOf(getItem(pos).getAttempts()));
                return convertView;
            }
        };

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // i = new Intent(Ranking.this, MainActivity.class);
                // startActivity(i);
                startActivity(new Intent(Ranking.this, MainActivity.class));

            }
        });

        lv.setAdapter(adapter);
    }
}
