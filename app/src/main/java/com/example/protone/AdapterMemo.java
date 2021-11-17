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

import java.util.ArrayList;

public class AdapterMemo extends RecyclerView.Adapter<AdapterMemo.CustomViewHolder> {

    Context context;
    ArrayList<DTOmemo> arrayList ;
    ArrayList<String> arrayKey;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ShowMemo showMemo;

    AdapterMemo() { }


    AdapterMemo(ArrayList<DTOmemo> arrayList, ArrayList<String> arrayKey) {
        this.arrayList = arrayList;
        this.arrayKey = arrayKey;
    }





    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        this.context = parent.getContext();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("HEESUNG");
        showMemo = new ShowMemo(this.context);

        Log.d("AAAAA", "onCreateViewHolder:");

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMemo.CustomViewHolder holder, int position) {
        holder.tv.setText(arrayList.get(position).getTitle());
        holder.itemView.setTag(position);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemo.callfun(arrayList.get(position));
                Log.d("QQQQQ", ""+arrayKey.get(position)+"@@@"+arrayList.get(position).getTitle()+ arrayList.get(position).getContent());
            }
        });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"수정하기","삭제하기","취소"};
                AlertDialog.Builder selectDialog = new AlertDialog.Builder(context);
                selectDialog.setTitle("항목선택");
                selectDialog.setCancelable(false);

                selectDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 수정하기
                                CustomDialog customDialog = new CustomDialog(context);
                                customDialog.callfun(arrayList.get(position));
                                customDialog.setCustomDialoginfoListener(new CustomDialog.CustomDialoginfoListener() {
                                    @Override
                                    public void infoshot(DTOmemo data) {
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

                selectDialog.show();




//                databaseReference.child(arrayKey.get(position)).removeValue();
//                Log.d("CCCC", ""+arrayKey.get(position)+"@@@"+arrayList.get(position));
//                arrayList.remove(position);
//                arrayKey.remove(position);
//
//                notifyDataSetChanged();
                return true;
            }
        });

        Log.d("AAAAAA", "onBindViewHolder:");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv = itemView.findViewById(R.id.rv_Title);
            Log.d("AAAAA", "CustomViewHolder:");
        }
    }

    public void setArray(ArrayList<DTOmemo> l, ArrayList<String> k) {
        this.arrayKey = k;
        this.arrayList = l;
        notifyDataSetChanged();
    }
}
