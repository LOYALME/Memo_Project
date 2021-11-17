package com.example.protone;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragMemo extends Fragment {


    Context context;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DTOmemo> arrayList;
    ArrayList<String> arrayKey;
    AdapterMemo adapterMemo;

    FirebaseDatabase db;
    DatabaseReference df;

    @Override
    public void onPause() {
        super.onPause();
        Log.d("QWEWWQQW", "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<DTOmemo> resetList = new ArrayList<>();
        ArrayList<String> resetKey = new ArrayList<>();
        if(adapterMemo != null)
        {
            df.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    resetList.clear();
                    resetKey.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DTOmemo data = dataSnapshot.getValue(DTOmemo.class);
                        String key = dataSnapshot.getKey();

                        resetList.add(data);
                        resetKey.add(key);
                    }

                    adapterMemo.setArray(resetList, resetKey);
                    adapterMemo.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }


        Log.d("AAAAA", "onResume:");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapterMemo = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_memo, container, false);
        this.context = v.getContext();
        rv = v.findViewById(R.id.memorv);
        db = FirebaseDatabase.getInstance();
        df = db.getReference("HEESUNG");
        arrayList = new ArrayList<>();
        arrayKey = new ArrayList<>();

        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                arrayKey.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DTOmemo data = dataSnapshot.getValue(DTOmemo.class);
                    String key = dataSnapshot.getKey();

                    arrayList.add(data);
                    arrayKey.add(key);
                }
                adapterMemo.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        layoutManager = new LinearLayoutManager(getActivity());
        adapterMemo = new AdapterMemo(arrayList, arrayKey);




        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapterMemo);






        Log.d("AAAAA", "onCreateView: ");
        return v;

    }

    public void fresh()  {
        adapterMemo.notifyDataSetChanged();
    }
}