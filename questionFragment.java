package com.example.joousope.bakdinle;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Interface.ItemClickListener;
import com.example.joousope.bakdinle.ViewHolder.QuestionViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;


public class questionFragment extends Fragment {

    View myFragment;
    RecyclerView listQuestions;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Question,QuestionViewHolder> adapter;
    FirebaseDatabase database;
    ArrayList<Question> question;
    DatabaseReference questions;

    public static questionFragment newInstance()
    {
        questionFragment questionFragment=new questionFragment();
        return questionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myFragment=LayoutInflater.from(getContext()).inflate(R.layout.fragment_question,false);
        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Question");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_question,container,false);
        //myFragment=inflater.inflate(R.layout.fragment_question,container,false);
        listQuestions=(RecyclerView)myFragment.findViewById(R.id.listQuestion);
        listQuestions.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listQuestions.setLayoutManager(layoutManager);
        loadQuestions();

        /*database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");
        questions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Question value=dataSnapshot.getValue(Question.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        return myFragment;
    }
    private void loadQuestions() {
        adapter=new FirebaseRecyclerAdapter<Question, QuestionViewHolder>(Question.class,R.layout.question_layout,QuestionViewHolder.class,questions) {
            @Override
            protected void populateViewHolder(QuestionViewHolder viewHolder, Question model, int position) {
                viewHolder.Name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.Image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame = new Intent(getActivity(), Start.class);
                        Common.CategoryId = adapter.getRef(position).getKey();
                        startActivity(startGame);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listQuestions.setAdapter(adapter);
    }
}
