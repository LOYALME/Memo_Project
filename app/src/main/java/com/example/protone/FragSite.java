package com.example.protone;

import android.content.Context;
import android.os.Bundle;
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

public class FragSite extends Fragment {

    Context context;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    AdapterSite adapterSite;

    ArrayList<DTOsite> arrayList;
    ArrayList<String> arrayKey;


    @Override
    public void onResume() {
        super.onResume();

        if(adapterSite != null)
        {
            ArrayList<DTOsite> resetList = new ArrayList<>();
            ArrayList<String> resetKey = new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    resetList.clear();
                    resetKey.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DTOsite data = dataSnapshot.getValue(DTOsite.class);
                        String key = dataSnapshot.getKey();

                        resetList.add(data);
                        resetKey.add(key);
                    }

                    adapterSite.setArray(resetList, resetKey);
                    adapterSite.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_site, container, false);
        this.context = v.getContext();
        rv = v.findViewById(R.id.siterv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        arrayKey = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("SITE");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                arrayKey.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //SiteData class 생성자(매개변수가 없는)가 필요함
                    DTOsite s = snapshot.getValue(DTOsite.class);
                    String k = snapshot.getKey();
                    arrayKey.add(k);
                    arrayList.add(s);

                }
                adapterSite.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapterSite = new AdapterSite(arrayList, arrayKey);
        rv.setAdapter(adapterSite);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapterSite = null;
    }
}