package com.example.protone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btnavi;
    FloatingActionButton floatingActionButton;
    FrameLayout frameLayout;

    FragMemo fragmemo;
    FragSite fragSite;
    FirebaseDatabase database;
    DatabaseReference databaseReference, databaseReferenceSite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("포트폴리오 - 메모앱");
        frameLayout = findViewById(R.id.frame);
        btnavi = findViewById(R.id.bottomNavi);
        floatingActionButton = findViewById(R.id.floatingbtn);
        fragmemo = new FragMemo();
        fragSite = new FragSite();
        setFrag(0);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("HEESUNG");
        databaseReferenceSite = database.getReference("SITE");

        floatingActionButton.setOnClickListener(onClickListener);


        btnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.memo :
                        setFrag(0);
                        break;

                    case R.id.site:
                        setFrag(1);
                        break;
                }
                return true;
            }
        });


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame);

            switch (v.getId()) {
                case R.id.floatingbtn:

                    if(f instanceof FragMemo) {
                        Intent intent = new Intent(MainActivity.this, Editmemo.class);
                        startActivityForResult(intent, 999);


                    } else if (f instanceof FragSite) {
                        Intent intent = new Intent(MainActivity.this, Editsite.class);
                        startActivityForResult(intent , 9999);

                    } else { }

                    break;

            }

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QWEWWQQW", "Main - onPause: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 999:
                    String title = data.getStringExtra("title");
                    String con = data.getStringExtra("con");

                    DTOmemo d = new DTOmemo(title, con);
                    databaseReference.push().setValue(d);
                    break;

                case 9999:
                    Log.d("AAAA", "사이트관리에디터 갓다옴");
                    String site = data.getStringExtra("site");
                    String id = data.getStringExtra("id");
                    String pw = data.getStringExtra("pw");

                    DTOsite dtOsite = new DTOsite(site, id, pw);
                    databaseReferenceSite.push().setValue(dtOsite);
                    break;

                default:
                    break;
            }
        }

    }

    public void setFrag(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (position) {
            case 0:
                ft.replace(R.id.frame, fragmemo);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.frame, fragSite);
                ft.commit();
                break;

            default:
                break;
        }
    }


}



































