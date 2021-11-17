package com.example.protone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Editmemo extends AppCompatActivity {

    EditText title, con;
    Button finish, cancle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmemo);


        title = findViewById(R.id.edittitle);
        con = findViewById(R.id.editcon);
        finish = findViewById(R.id.finish_btn);
        cancle = findViewById(R.id.cancle_btn);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String c = con.getText().toString();

                if(t.equals("") || c.equals("") || t ==null || c == null){
                    Toast.makeText(getApplicationContext(), "빈공간이 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("title", t);
                    intent.putExtra("con", c);
                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


}