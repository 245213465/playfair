package com.example.wwk.playfair;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private char[] letters = {'A','B','C','D','E','F','G','H','I','K','L','M','N',
    'O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private int[] exist;    //判断字母是否填入矩阵
    private String miyao;
    private char[] transmission = new char[25]; //在活动活动传递mat
    private char[] miyaos;
    private char[][] mat;       //5*5矩阵

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mat = new char[5][5];

        final EditText cipher = (EditText)findViewById(R.id.cipher_input);
        Button sure_cipher = (Button)findViewById(R.id.sure_cipher);


        sure_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exist = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                miyao = new String(cipher.getText().toString());
                miyao =  miyao.replace('j','i');         //将密钥中的 j 替换成 i
                miyao = miyao.toString().toUpperCase(); //转换大写
                Log.d("main_miyao",miyao);
                miyaos = new char[miyao.length()];
                Log.d("miyao",miyao);
                int len = miyao.length();
                int i;
                for(i = 0;i < len;i++){
                    miyaos[i] = miyao.charAt(i);    //将string里的字符放入char【】
                }
                Log.d("mimi",String.valueOf(miyaos));
                Log.d("mimi.length",String.valueOf(miyaos.length));

                int l=0,h=0;
                for(i = 0;i <len;i++){      //将密钥中的字母按次序放入矩阵mat
                    for(int k = 0;k < 25;k++){
                        if(miyaos[i] == letters[k]&&exist[k] == 0){
                            mat[h][l] = miyaos[i];
                            exist[k] = 1;
                            if(l < 4)
                                l++;
                            else{
                                h++;
                                l=0;
                            }
                        }

                    }
                }
                Log.d("l+h",String.valueOf(l)+ String.valueOf(h));

                for(i = 0;i < 25;i++){      //        将字母表中除密钥之外的字母按顺序填入矩阵mat
                    if(exist[i] == 0){
                        mat[h][l] = letters[i];
                        exist[i] = 1;
                        if(l< 4)
                            l++;
                        else{
                            h++;
                            l=0;
                        }
                    }
                }




                Log.d("main","mat:");
                for(int k = 0;k < 5;k ++){
                    for(int d = 0;d < 5;d++) {
                        System.out.println(mat[k][d]);
                    }
                }
                Log.d("main","miyaos+++:");
                int j = 0;
                for(int k = 0;k < 5;k ++){
                    for(int d = 0;d < 5;d++) {
                        transmission[j] = mat[k][d];
                        Log.d("main—tra",String.valueOf(transmission[j]));
                        j++;
                    }
                }
                Intent intent = new Intent(MainActivity.this,encrypt_decrypt.class);
                intent.putExtra("mat",transmission);     //将5*5的矩阵传入encrypt_decrypt活动
                startActivity(intent);
            }
        });
    }
}
