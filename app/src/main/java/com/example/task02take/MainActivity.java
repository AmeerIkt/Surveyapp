package com.example.task02take;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button start, exit;
    TextView lastScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String username = getIntent().getStringExtra("username");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        int lastScore = databaseHelper.getLastScore(username);
        String lastTimestamp = databaseHelper.getLastScoreTimestamp(username);




        lastScoreTextView = findViewById(R.id.lastScoreTextView);
        lastScoreTextView.setText("Last Score: " + lastScore + " (Time: " + lastTimestamp + ")");

        start = findViewById(R.id.startbutton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Surveytemplate.class);
                intent.putExtra("username", username);
                startActivity(intent);
                int lastScore = databaseHelper.getLastScore(username);
                String lastTimestamp = databaseHelper.getLastScoreTimestamp(username);



                lastScoreTextView.setText("Last Score: " + lastScore + " (Time: " + lastTimestamp + ")");

            }
        });

        exit = findViewById(R.id.ExitButton);
        exit.setOnClickListener(v -> finish());
    }
}
