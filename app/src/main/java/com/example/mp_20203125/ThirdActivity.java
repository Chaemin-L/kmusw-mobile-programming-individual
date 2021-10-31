package com.example.mp_20203125;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.io.InputStream;

public class ThirdActivity extends AppCompatActivity {

    ActivityResultLauncher resultLauncher;
    Button add, delete, profile;
    ListView listview;
    ListViewAdapter adapter;
    String name;
    AlertDialog.Builder input, info, signUp;
    EditText nameInput;
    Bitmap newImg;
    ViewGroup parentView; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_third);

        add = findViewById(R.id.addItemBtn);
        delete = findViewById(R.id.delItemBtn);
        profile = findViewById(R.id.profileBtn);

        listview = findViewById(R.id.itemList);
        adapter = new ListViewAdapter();
        listview.setAdapter(adapter);

        // 상품 추가시 사용자로부터 입력받을 입력 Dialog: input
        nameInput = new EditText(getApplicationContext());
        parentView= (ViewGroup) nameInput.getParent();
        input = new AlertDialog.Builder(ThirdActivity.this)
                .setTitle("상품 이름")
                .setMessage("등록할 상품의 이름을 입력해주세요.")
                .setView(nameInput)
                .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        name = nameInput.getText().toString();
                        adapter.addBitmapItem(newImg, name);
                        adapter.notifyDataSetChanged();
                        nameInput.setText("");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        // 회원 정보를 띄울 Dialog: info
        info = new AlertDialog.Builder(ThirdActivity.this)
                .setTitle("회원 정보")
                .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        // 비회원이 회원정보 클릭시 회원가입 희망 유무를 질의할 Dialog: signUp
        signUp = new AlertDialog.Builder(ThirdActivity.this)
                .setTitle("회원가입")
                .setMessage("회원가입하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // Third -> Second(회원가입) -> Main Activity에 이르기까지
                        // startActivity에 따른 Activity 엉킴을 방지하기 위해,
                        // 초기 Main Activity와 다르게 flag 값을 intent로 전달하여 구분 후 별도 처리
                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        intent.putExtra("flag", true);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });


        adapter.addDrawableItem(ContextCompat.getDrawable(this, R.drawable.foil),"Foil");
        adapter.addDrawableItem(ContextCompat.getDrawable(this,R.drawable.spatula), "Silicon spatula");
        adapter.addDrawableItem(ContextCompat.getDrawable(this,R.drawable.tray), "Muffin tray");
        adapter.addDrawableItem(ContextCompat.getDrawable(this, R.drawable.handmixer), "Hand mixer");
        adapter.addDrawableItem(ContextCompat.getDrawable(this,R.drawable.chocolate), "Coating chocolate");

        AlertDialog alert = input.create();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newImg = null; name = null;
                // 갤러리로 이동하기 전에 상품 이름을 요청하는 Dialog가 뜨는 걸 방지하기 위해
                // Handler의 postDelayed 이용하여 갤러리가 열리기까지의 지연 시간을 부여.
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                resultLauncher.launch(intent);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        alert.show();
                    }
                }, 2000);



            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt, checked;
                cnt = adapter.getCount();

                if(cnt>0){
                    checked = listview.getCheckedItemPosition();

                    if(checked > -1 && checked < cnt){
                        adapter.deleteItem(checked);
                        listview.clearChoices();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("member_info",0);
                String loginID = prefs.getString("#NOW#LOGIN", "");
                if(loginID.equals("")){
                    AlertDialog dialog = signUp.create();
                    dialog.show();
                }else {
                    String name = "이름: " + prefs.getString(loginID+"_name","");
                    String id = "아이디: " + prefs.getString(loginID,"");
                    String pw = "비밀번호: " + prefs.getString(loginID+"_PW","");
                    String address = "주소: " + prefs.getString(loginID+"_address","");
                    String phoneNum = "휴대폰 번호: " + prefs.getString(loginID+"_phoneNum","");
                    info.setMessage(name+"\n"+id+"\n"+pw+"\n"+address+"\n"+phoneNum);
                    AlertDialog dialog = info.create();
                    dialog.show();

                }
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            Uri selectedImageUri = intent.getData();
                            try {
                                InputStream in = getContentResolver().openInputStream(selectedImageUri);
                                newImg = BitmapFactory.decodeStream(in);
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }
}

