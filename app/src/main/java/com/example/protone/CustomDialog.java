package com.example.protone;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog {


    Context context;
    EditText t , c;
    Button finish, cancle;
    CustomDialoginfoListener customDialoginfoListener;


    CustomDialog() { }

    CustomDialog(Context c) {
        this.context = c;
    }

    interface CustomDialoginfoListener{
        void infoshot(DTOmemo data);
    }

    public void setCustomDialoginfoListener( CustomDialoginfoListener customDialoginfoListener){
        this.customDialoginfoListener = customDialoginfoListener;
    }


    public void callfun(DTOmemo data){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.editlayout);
        dialog.show();


        t = dialog.findViewById(R.id.dialog_title);
        c = dialog.findViewById(R.id.dialog_con);
        finish = dialog.findViewById(R.id.finish);
        cancle = dialog.findViewById(R.id.cancle);

        t.setText(data.getTitle());
        c.setText(data.getContent());

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = t.getText().toString();
                String con = c.getText().toString();
                customDialoginfoListener.infoshot(new DTOmemo(title, con));
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
