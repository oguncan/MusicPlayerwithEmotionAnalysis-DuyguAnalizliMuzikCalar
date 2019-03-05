package com.example.joousope.bakdinle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.joousope.bakdinle.Common.SongCommon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start2 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    Button buttonDinle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Songs");
        loadSongs(SongCommon.SongCategoryId);
        buttonDinle=(Button)findViewById(R.id.btnDinle);
        buttonDinle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start2.this,SongListen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadSongs(final String SongCategoryId) {
        if(SongCommon.songList.size()>0)
            SongCommon.songList.clear();

        reference.orderByChild("SongCategoryId").equalTo(SongCategoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    SongInfo songInfo=postSnapshot.getValue(SongInfo.class);
                    SongCommon.songList.add(songInfo);
                    }
                }@Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Collections.shuffle(SongCommon.songList);
        }
    }

