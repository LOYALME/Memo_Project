package com.example.protone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterSite extends RecyclerView.Adapter<AdapterSite.CustomViewHolder> {


    Context context;
    ShowSite showSite;


    ArrayList<DTOsite> arrayList;
    ArrayList<String> arrayKey;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    AdapterSite() {   }

    AdapterSite(ArrayList<DTOsite> list, ArrayList<String> key) {
        this.arrayList = list;
        this.arrayKey = key;
    }




    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        this.context = parent.getContext();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("SITE");
        showSite = new ShowSite(this.context);


        Log.d("AAAAA", "onCreateViewHolder:");

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSite.CustomViewHolder holder, int position) {

        holder.tv.setText(arrayList.get(position).getSitename());
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSite.callfun(arrayList.get(position));


            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"수정하기", "삭제하기", "취소"};
                AlertDialog.Builder selectDialogs = new AlertDialog.Builder(context);
                selectDialogs.setTitle("항목선택");
                selectDialogs.setCancelable(false);


                selectDialogs.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 수정하기
                                CustomDialogS customDialogs = new CustomDialogS(context);
                                customDialogs.callfun(arrayList.get(position));
                                customDialogs.setCustomDialoginfoListener(new CustomDialogS.CustomDialoginfoListener() {
                                    @Override
                                    public void infoshot(DTOsite data) {
                                        databaseReference.child(arrayKey.get(position)).setValue(data);
                                        arrayList.set(position, data);
                                        Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                });

                                break;

                            case 1: //삭제하기
                                databaseReference.child(arrayKey.get(position)).removeValue();

                                arrayList.remove(position);
                                arrayKey.remove(position);
                                Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                                break;

                            case 2:
                                Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });



                selectDialogs.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {     return arrayList.size();   }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv = itemView.findViewById(R.id.rv_Title);

        }
    }

    public void setArray(ArrayList<DTOsite> list, ArrayList<String> k ) {
        this.arrayList = list;
        this.arrayKey = k;
        notifyDataSetChanged();
    }
}
