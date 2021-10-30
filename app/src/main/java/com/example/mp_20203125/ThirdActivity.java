package com.example.mp_20203125;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.io.InputStream;


public class ThirdActivity extends AppCompatActivity {

    ActivityResultLauncher resultLauncher;

    Button add, delete;
    ListView listview;
    ListViewAdapter adapter;
    String name;
    AlertDialog.Builder input;
    EditText nameInput;
    Bitmap newImg;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        add = findViewById(R.id.addItemBtn);
        delete = findViewById(R.id.delItemBtn);


        listview = findViewById(R.id.itemList);
        adapter = new ListViewAdapter();
        listview.setAdapter(adapter);

        // 상품 추가시 사용자로부터 입력받을 입력 팝업창
        nameInput = new EditText(getApplicationContext());
        input = new AlertDialog.Builder(ThirdActivity.this)
                .setTitle("상품 이름")
                .setMessage("등록할 상품의 이름을 입력해주세요.")
                .setView(nameInput)
                .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        name = nameInput.getText().toString();
                        adapter.addItem(newImg, name);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        // 기본적으로 출력되는 상품들
        // 1. drawable 객체를 bitmap으로 변환
        Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.foil);
        Bitmap img2 = BitmapFactory.decodeResource(getResources(), R.drawable.spatula);
        Bitmap img3 = BitmapFactory.decodeResource(getResources(), R.drawable.tray);
        Bitmap img4 = BitmapFactory.decodeResource(getResources(), R.drawable.handmixer);
        Bitmap img5 = BitmapFactory.decodeResource(getResources(), R.drawable.chocolate);

        // 2. 변환된 객체를 리스트뷰에 추가
        adapter.addItem(img1, "Foil");
        adapter.addItem(img2, "Silicon spatula");
        adapter.addItem(img3, "Muffin tray");
        adapter.addItem(img4, "Hand mixer");
        adapter.addItem(img5, "Coating chocolate");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                resultLauncher.launch(intent);

                // 갤러리로 이동하기 전에 상품 이름을 요청하는 팝업 창이 뜨는 걸 방지하기 위해
                // 쓰레드를 이용하여 갤러리가 열리기까지의 충분한 지연 시간을 부여.
                BackgroundThread thread = new BackgroundThread();
                thread.start();
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

    class BackgroundThread extends Thread {
        public void run() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alert = input.create();
                    alert.show();
                }
            }, 3000);
        }
    }
}

