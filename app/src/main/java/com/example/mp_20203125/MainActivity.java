package com.example.mp_20203125;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button signUpBtn, loginBtn, guestBtn;
    EditText ID, PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ID = findViewById(R.id.ID_input);
        PW = findViewById(R.id.PW_input);

        signUpBtn = findViewById(R.id.SignUpBtn);
        loginBtn = findViewById(R.id.loginBtn);
        guestBtn = findViewById(R.id.withoutLoginBtn);

        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            String id = ID.getText().toString();
            String pw = PW.getText().toString();
            if (id.equals("") || pw.equals("")) {
                Toast.makeText(getApplicationContext(), "아이디와 비번 모두 입력해주세요.", LENGTH_SHORT).show();
                return;
            }
            SharedPreferences prefs = getSharedPreferences("member_info", 0);
            if (prefs.contains(id)) {
                String matchedPW = id + "_" + "PW";
                if (pw.equals(prefs.getString(matchedPW, ""))) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("#NOW#LOGIN", id);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호가 일치하지 않습니다.", LENGTH_SHORT).show();
                    }
            } else {
                Toast.makeText(getApplicationContext(), "존재하지 않는 계정입니다.", LENGTH_SHORT).show();
            }

        });

        guestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
            startActivity(intent);
        });

    }
}