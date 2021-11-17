package com.example.protone;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowSite {
    Context context;
    TextView title, id, pw;
    Button finishbtu;

    ShowSite(Context c) {this.context = c;}

    public void callfun(DTOsite m){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.showsite);
        dialog.show();

        title = dialog.findViewById(R.id.dialog_title);
        id = dialog.findViewById(R.id.dialog_id);
        pw = dialog.findViewById(R.id.dialog_pw);
        finishbtu = dialog.findViewById(R.id.dialog_finish);

        title.setText("사이트명 : " + m.getSitename());
        id.setText("ID : " + m.getId());
        pw.setText("패스워드 : " +m.getPw());

        finishbtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"닫기",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




    }

}
