package com.example.wwk.playfair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by wwk on 2016/4/28.
 */
public class encrypt_decrypt extends AppCompatActivity  {

    private char[] transmission;
    private Bundle bundle;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.encrypt_decrypt);

        Intent intent1 = getIntent();
       transmission = intent1.getCharArrayExtra("mat");

        Button encrypt = (Button)findViewById(R.id.encrypt);
        Button decrypt = (Button)findViewById(R.id.decrypt);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(encrypt_decrypt.this,input_en.class);
                intent.putExtra("mat",transmission);       //将5*5的矩阵传入下一个活动
                startActivity(intent);
            }
        });
       decrypt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(encrypt_decrypt.this,input_de.class);
               intent.putExtra("mat",transmission);        //将5*5的矩阵传入下一个t活动
               startActivity(intent);
           }
       });

    }
}
