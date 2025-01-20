package com.example.task02take;

import android.content.Intent;
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

public class Login extends AppCompatActivity {
    private EditText username, password;
    private Button loginButton , registerButton;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        username = findViewById(R.id.usernameinput);
        password = findViewById(R.id.passwordinput);
        loginButton = findViewById(R.id.loginbutton);
        registerButton = findViewById(R.id.registerbutton);
        databaseHelper = new DatabaseHelper(this);

        databaseHelper.addUser ( "user" , "pass");





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamedata = username.getText().toString().trim();
                String passworddata = password.getText().toString().trim();

                if (databaseHelper.validateUser(usernamedata, passworddata)) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("username", usernamedata);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Login.this, register.class);
                startActivity(intent);

            }
        });



    }
}