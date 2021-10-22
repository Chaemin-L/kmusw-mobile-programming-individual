package com.example.mp_20203125;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button signUpBtn, loginBtn, guestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpBtn = findViewById(R.id.SignUpBtn);
        loginBtn = findViewById(R.id.loginBtn);
        guestBtn = findViewById(R.id.withoutLoginBtn);

        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
            startActivity(intent);
        });

        guestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
            startActivity(intent);
        });

    }
}