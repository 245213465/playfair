package com.example.wwk.playfair;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by wwk on 2016/4/28.
 */
public class input_en extends AppCompatActivity {

    private char[][] mat;
    private char[] en_char;
    private String mi_mat = "";
    private String en;
    private char[] transmission;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.input_en);

        mat = new char[5][5];

        Intent intent1 = getIntent();
        transmission = intent1.getCharArrayExtra("mat");

        int p = 0;
        for(int i = 0;i < 5;i ++){
            for(int j = 0;j < 5;j++) {
                mat[i][j] = transmission[p];
                Log.d("mat!!",String.valueOf(mat[i][j]));
                p++;
            }
        }
        final EditText input_en= (EditText)findViewById(R.id.input_en);
        Button sure_input_en = (Button)findViewById(R.id.sure_input_en);
        TextView mi_en = (TextView)findViewById(R.id.mi_en);
        final TextView en_change = (TextView)findViewById(R.id.en_change);



        sure_input_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                en_char = new char[100];
                java.util.Arrays.fill(en_char,' ');
                en = input_en.getText().toString();
                en = en.replace('j','i');       //把明文中的j替换成i
                en = en.toString().toUpperCase();           //把明文准转换成大写
                int len = en.length();      //获得明文长度

                for(int i = 0;i < len;i++){
                    en_char[i] = en.charAt(i);    //将明文en里的字符放入char【】
                }

                //将明文两两划分，当分组中出现字符相同时，填充‘X’使相同字符分隔
                for(int i = 0;i < (len = new String(en_char).trim().length());i+=2){
                    Log.d("en_len",String.valueOf( new String(en_char).trim().length()));
                    if(en_char[i] == en_char[i+1]) {
                        for (int k = (len = new String(en_char).trim().length()); k > i+1; k--) {
                            Log.d("en_len2",String.valueOf( new String(en_char).trim().length()));
                            en_char[k] = en_char[k - 1];
                        }
                        en_char[i + 1] = 'X';
                    }
                }
                Boolean flag;
                for(int q = 0;q <(len = new String(en_char).trim().length());q++)
                    Log.d("input_en_char1",String.valueOf(en_char[q]));
                if((len = new String(en_char).trim().length()) % 2 != 0){        //如果分组完字符数为奇数则在字符串末尾加‘X’
                    en_char[(len = new String(en_char).trim().length())] = 'X';
                }
                for(int q = 0;q <(len = new String(en_char).trim().length());q++)
                    Log.d("input_en_char",String.valueOf(en_char[q]));


                for(int i = 0;i < (len = new String(en_char).trim().length());i+=2){     //对每个分组进行加密
                    int m1,n1 = 0,m2,n2 = 0;
                    flag = false;
                    for(m1 = 0; m1 < 5;m1++){       //获得分组第一个字符行、列
                        for(n1 = 0;n1 < 5;n1++) {
                            Log.d("mat_mn",String.valueOf(mat[m1][n1]));
                            if (en_char[i] == mat[m1][n1]){
                                Log.d("mat_mn_en",String.valueOf(en_char[i]));
                                flag = true;
                                break;
                            }
                        }
                        if(flag == true)
                            break;
                    }
                    flag = false;
                    for(m2 = 0; m2 < 5;m2++){       //获得分组第二个字符行、lie
                        for(n2 = 0;n2 < 5;n2++) {
                            Log.d("mat_mn2",String.valueOf(mat[m2][n2]));
                            if (en_char[i+1] == mat[m2][n2]){
                                Log.d("mat_mn_en",String.valueOf(en_char[i+1]));
                                flag = true;
                                break;
                            }
                        }
                        if(flag == true)
                            break;
                    }
                    Log.d("m1+n2+m2+n2",String.valueOf(m1)+String.valueOf(n1)+String.valueOf(m2)+String.valueOf(n2));

                    Log.d("m1+n2+m2+n2___",String.valueOf(m1)+String.valueOf(n1)+String.valueOf(m2)+String.valueOf(n2));

                    if(m1 == m2){       //该分组两个字符在矩阵相同行
                        en_char[i] = mat[m1][(n1+2)%5];
                        en_char[i+1] = mat[m2][(n2+2)%5];
                        Log.d("mat_mn_chang",String.valueOf(en_char[i]));
                        Log.d("mat_mn_chang2",String.valueOf(en_char[i+1]));
                    }
                    else {
                        if(n1 == n2){   //该分组两个字符在矩阵同列
                            en_char[i] = mat[(m1+2)%5][n1];
                            en_char[i+1] = mat[(m2+2)%5][n2];
                            Log.d("mat_mn_chang",String.valueOf(en_char[i]));
                            Log.d("mat_mn_chang2",String.valueOf(en_char[i+1]));
                        }
                        else{       //该分组两个字符在矩阵呈对角
                            en_char[i] = mat[m1][n2];
                            en_char[i+1] = mat[m2][n1];
                            Log.d("mat_mn_chang",String.valueOf(en_char[i]));
                            Log.d("mat_mn_chang2",String.valueOf(en_char[i+1]));
                        }
                    }
                }
                        en = new String(en_char);   //将en_char内容放入string en中
                        en_change.setText(en);
                    }

        });
        mi_mat = new String(String.valueOf(transmission));//显示密码表矩阵
        mi_en.setText(mi_mat.substring(0,5)+"\n"+mi_mat.substring(5,10)+"\n"
        +mi_mat.substring(10,15)+"\n"+mi_mat.substring(15,20)+"\n"+mi_mat.substring(20,25));
    }
}
