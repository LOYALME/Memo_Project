package com.example.protone;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialogS {


    Context context;
    EditText t , c, p;
    Button finish, cancle;
    CustomDialoginfoListener customDialoginfoListener;


    CustomDialogS() { }

    CustomDialogS(Context c) {
        this.context = c;
    }

    interface CustomDialoginfoListener{
        void infoshot(DTOsite data);
    }

    public void setCustomDialoginfoListener( CustomDialoginfoListener customDialoginfoListener){
        this.customDialoginfoListener = customDialoginfoListener;
    }


    public void callfun(DTOsite data){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.editlayouts);
        dialog.show();


        t = dialog.findViewById(R.id.dialog_title);
        c = dialog.findViewById(R.id.dialog_con);
        p = dialog.findViewById(R.id.dialog_pw);
        finish = dialog.findViewById(R.id.finish);
        cancle = dialog.findViewById(R.id.cancle);


        t.setText(data.getSitename());
        c.setText(data.getId());
        p.setText(data.getPw());

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = t.getText().toString();
                String con = c.getText().toString();
                String pw = p.getText().toString();
                customDialoginfoListener.infoshot(new DTOsite(title, con, pw));
                Toast.makeText(context,"수정완료",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"닫기",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });






    }





}
