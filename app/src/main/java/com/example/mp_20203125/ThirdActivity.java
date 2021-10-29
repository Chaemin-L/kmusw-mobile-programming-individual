package com.example.mp_20203125;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class ThirdActivity extends AppCompatActivity {
    
    ActivityResultLauncher resultLauncher;

    Button add, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        add = findViewById(R.id.addItemBtn);
        delete = findViewById(R.id.delItemBtn);

        ListView listview;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        listview = findViewById(R.id.itemList);
        listview.setAdapter(adapter);

        // 기본적으로 출력되는 상품들
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.foil), "Foil") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.spatula), "Silicon spatula") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.tray), "Muffin tray") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.handmixer), "Hand mixer") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.chocolate), "Coating chocolate") ;

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                resultLauncher.launch(intent);
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent intent = result.getData();
                            String img = intent.getStringExtra("imgSrc");
                        }
                    }
                }
        );
    }


}
