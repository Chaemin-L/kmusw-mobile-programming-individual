package com.example.mp_20203125;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


public class AddItemActivity extends Activity {

    ImageButton select;
    String imgSrc;
    Button check, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_additem);


        check = findViewById(R.id.check);
        cancel = findViewById(R.id.cancel);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent = new Intent(getApplicationContext(), ThirdActivity.class);
                outIntent.putExtra("imgSrc", imgSrc);
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void imgSelect (View v){
        if(select != null){
            select.setClickable(true);
        }
        select =  (ImageButton) v;
        select.setClickable(false);
        imgSrc = "@drawable/" + v.getId();
    }
}
