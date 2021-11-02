package com.example.mp_20203125;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends AppCompatActivity {
    /* ID와 PW의 제약 조건을 만족하는지 확인하는 변수 */
    boolean idPass = false;
    boolean pwPass = false;
    boolean termsAgree = false;

    Button checkID, saveBtn;
    RadioGroup selectedAgree;
    EditText ID_input, PW_input, name_input, address_input, phoneNum_input;
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
        name_input = findViewById(R.id.name_input);
        address_input = findViewById(R.id.address_input);
        phoneNum_input = findViewById(R.id.phoneNum_input);
        selectedAgree = findViewById(R.id.seletedAgree);
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
                // 아이디에 빈 문자열 등록 방지
                if(!ID_input.getText().toString().equals("")){
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

            }
        });

        // 선택된 라디오 버튼에 따른 약관 동의 여부 변수 termsAgree에 저장.
        selectedAgree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selected) {
                switch(selected){
                    case R.id.acceptBtn:
                        termsAgree = true;
                        break;
                    case R.id.declineBtn:
                        termsAgree = false;
                        break;
                }
            }
        });


        // 저장 버튼 클릭시 모든 조건을 만족했을때 저장하도록 하며,
        // 그 외의 경우에는 사용자에게 요구사항 출력.
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(idPass && pwPass && termsAgree){
                    // 사용자 입력값 저장
                    SharedPreferences prefs = getSharedPreferences("member_info",0);
                    SharedPreferences.Editor editor = prefs.edit();
                    String ID = ID_input.getText().toString();
                    String PW = PW_input.getText().toString();
                    String name = name_input.getText().toString();
                    String address = address_input.getText().toString();
                    String phoneNum = phoneNum_input.getText().toString();


                    // 아이디는 계정 마다 고유한 값을 가지므로
                    // 아이디를 기준으로 다른 정보들을 알 수 있도록 키를 설정 후 저장
                    editor.putString(String.format("%s", ID ), ID);
                    editor.putString(String.format("%s_PW", ID ), PW);
                    editor.putString(String.format("%s_name", ID), name);
                    editor.putString(String.format("%s_address", ID ), address);
                    editor.putString(String.format("%s_phoneNum", ID ), phoneNum);
                    editor.apply();

                    // 세번째에서 회원정보 버튼을 통해 넘어온 거라면,
                    // 다시 첫번째 화면으로 보내 로그인 확인 후 finish()하도록 설정
                    if(getIntent().getBooleanExtra("flag", false)) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
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
