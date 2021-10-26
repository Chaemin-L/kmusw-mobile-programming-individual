package com.example.mp_20203125;

import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends AppCompatActivity {
    /* ID와 PW의 제약 조건을 만족하는지 확인 */
    boolean idPass = false;
    boolean pwPass = false;
    boolean termsAgree = false;

    Button checkID, saveBtn;
    CheckBox agree;
    EditText ID_input, PW_input, address_input, phoneNum_input;
    TextView checkPW;

    final String[] symbol = {"~", "!", "@", "#", "$", "%", "^", "&", "*"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        checkID = findViewById(R.id.checkID);
        ID_input = findViewById(R.id.ID_input);
        PW_input = findViewById(R.id.PW_input);
        checkPW = findViewById(R.id.checkPW);
        address_input = findViewById(R.id.address_input);
        phoneNum_input = findViewById(R.id.phoneNum_input);
        agree = findViewById(R.id.agreeBtn);
        saveBtn = findViewById(R.id.saveBtn);


        // 비밀번호 입력이 끝났을 때, 올바른 비밀번호인지 확인
        PW_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (PW_input.getText() != null){
                    if (PW_input.getText().toString().length() < 8) {
                        checkPW.setTextColor(Color.RED);
                        checkPW.setText("비밀번호는 8자 이상이어야합니다.");
                        checkPW.setVisibility(View.VISIBLE);
                        pwPass = false;
                    }
                    else{
                        int i=0;
                        while(i < symbol.length){
                            if(PW_input.getText().toString().contains(symbol[i])){
                                checkPW.setTextColor(Color.GREEN);
                                checkPW.setText("사용 가능");
                                pwPass = true;
                                break;
                            }
                            i++;
                        }
                        if(i == symbol.length){
                            checkPW.setTextColor(Color.RED);
                            checkPW.setText("비밀번호는 반드시 특수문자(~!@#$%^&*)를 하나 이상 포함해야합니다.");
                            checkPW.setVisibility(View.VISIBLE);
                            pwPass = false;
                        }
                    }
                }
            }
        });


        // 아이디 중복 체크 버튼 클릭시,
        // 사용자가 입력한 아이디와 이름이 같은 키를 SharedPreferences 객체에서 검색.
        checkID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SharedPreferences prefs = getSharedPreferences("member_info",0);
                    if(prefs.contains(ID_input.getText().toString())){
                        Toast.makeText(getApplicationContext(), "중복된 아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                        idPass = false;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                        idPass = true;
                    }

            }
        });


        // 저장 버튼 클릭시 모든 조건을 만족했을때 저장하도록 하며,
        // 그 외의 경우에는 어디에서 에러가 났는지 사용자에게 출력.
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                termsAgree = agree.isChecked();
                if(idPass && pwPass && termsAgree){
                    /* 사용자 데이터 저장 */
                    SharedPreferences prefs = getSharedPreferences("member_info",0);
                    SharedPreferences.Editor editor = prefs.edit();

                    String ID = ID_input.getText().toString();
                    String PW = PW_input.getText().toString();
                    String address = address_input.getText().toString();
                    String phoneNum = phoneNum_input.getText().toString();


                    // 아이디의 값은 계정 마다 고유한 값을 가지므로
                    // 아이디를 기준으로 다른 정보들을 알 수 있도록 키를 설정
                    editor.putString(String.format("%s", ID ), ID);
                    editor.putString(String.format("%s_PW", ID ), PW);
                    editor.putString(String.format("%s_address", ID ), address);
                    editor.putString(String.format("%s_phoneNum", ID ), phoneNum);
                    editor.apply();
                    finish();
                }
                else if(!idPass){
                    Toast.makeText(getApplicationContext(), "아이디 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!pwPass){
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 설정해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "회원가입을 위해서 약관 동의는 필수입니다.", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}
