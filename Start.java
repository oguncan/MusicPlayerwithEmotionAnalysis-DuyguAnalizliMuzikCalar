package com.example.joousope.bakdinle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Model.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {
    Button buttonPlay;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Questions");
        loadQuestions(Common.CategoryId);
        buttonPlay=(Button)findViewById(R.id.btnPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,Playing.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestions(final String CategoryId) {
        if(Common.questionsList.size()>0)
            Common.questionsList.clear();

        reference.orderByChild("CategoryId").equalTo(CategoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Questions question=postSnapshot.getValue(Questions.class);
                    Common.questionsList.add(question);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Collections.shuffle(Common.questionsList);

    }


}
