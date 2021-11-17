package com.example.protone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Editsite extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference databaseReference;


    EditText s, id, pw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsite);



        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("SITE");


        s = findViewById(R.id.edittitle);
        id = findViewById(R.id.editcon);
        pw = findViewById(R.id.editpw);


        findViewById(R.id.finish_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String site = s.getText().toString();
                String i = id.getText().toString();
                String p = pw.getText().toString();

                if(site.equals("") || i.equals("") || p.equals("") || site == null || i== null || p == null) {
                    Toast.makeText(getApplicationContext(),"빈공간이 존재합니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("site",site);
                    intent.putExtra("id",i);
                    intent.putExtra("pw",p);

                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        findViewById(R.id.cancle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}