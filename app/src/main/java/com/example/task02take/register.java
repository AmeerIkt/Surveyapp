package com.example.task02take;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class register extends AppCompatActivity {

    Button registerbutton , exitbutton ;
    private EditText username, password;

    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registerbutton = findViewById(R.id.registerpagebutton);
        exitbutton = findViewById(R.id.exitregisterpagebutton);

        username = findViewById(R.id.usernameinput);
        password = findViewById(R.id.passwordinput);

        databaseHelper = new DatabaseHelper(this);


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernamedata = username.getText().toString().trim();
                String passworddata = password.getText().toString().trim();

                if (!databaseHelper.isUsernameTaken(usernamedata)) {
                    databaseHelper.addUser(usernamedata , passworddata);
                    finish();
                } else {
                    Toast.makeText(register.this, " username already in use", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}