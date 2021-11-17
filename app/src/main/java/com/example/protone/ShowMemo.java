package com.example.protone;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMemo {
    Context context;
    TextView title, con;
    Button finishbtu;

    ShowMemo(Context c) {this.context = c;}

    public void callfun(DTOmemo m){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.showmemo);
        dialog.show();

        title = dialog.findViewById(R.id.dialog_title);
        con = dialog.findViewById(R.id.dialog_con);
        finishbtu = dialog.findViewById(R.id.dialog_finish);

        title.setText("제목 : " + m.getTitle());
        con.setText("내용 : " + m.getContent());

        finishbtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"닫기",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




    }

}
