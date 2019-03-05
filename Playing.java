package com.example.joousope.bakdinle;

import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Model.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.joousope.bakdinle.Common.Common.CategoryId;

public class Playing extends AppCompatActivity implements View.OnClickListener{
    final static long INTERVAL=1000;
    final static long TIMEOUT=12000;
    int progressValue=0;
    int index=0,thisQuestion=0,totalQuestion;
    ProgressBar progressBar;
    ImageView imageView;
    List<String> stringliste=new ArrayList<>();
    Button clickedButton;
    Button btnA,btnB,btnC,btnD,btnE,btnF;
    TextView txtQuestion,question_text,image_text;
    String cevap,metiin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //Views
        txtQuestion=(TextView)findViewById(R.id.txtTotalQuestion);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        question_text=(TextView)findViewById(R.id.question_text);
        btnA=(Button)findViewById(R.id.btnAnswerA);
        btnB=(Button)findViewById(R.id.btnAnswerB);
        btnC=(Button)findViewById(R.id.btnAnswerC);
        btnD=(Button)findViewById(R.id.btnAnswerD);
        btnE=(Button)findViewById(R.id.btnAnswerE);
        btnF=(Button)findViewById(R.id.btnAnswerF);
        imageView=(ImageView)findViewById(R.id.question_image);
        image_text=(TextView)findViewById(R.id.image_text);
        stringliste.add("1"); // Umut
        stringliste.add("2"); // Özlem
        stringliste.add("3"); // Aşk
        stringliste.add("4"); //Merak
        stringliste.add("5"); //Özgüven
        stringliste.add("6"); // Şefkat
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnE.setOnClickListener(this);
        btnF.setOnClickListener(this);
    }

    private void loadQuestions(final String CategoryId) {
        FirebaseDatabase database;
        DatabaseReference reference;
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Questions");
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

    @Override
    public void onClick(View v) {

        if(index<totalQuestion)
        {
            clickedButton = (Button) v;
            showQuestion(++index);
        }
        else
        {
            final Intent intent=new Intent(this,BackHome.class);
            FirebaseDatabase database;
            DatabaseReference reference;
            database=FirebaseDatabase.getInstance();
            reference=database.getReference("AnswerCategory");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long allNum = dataSnapshot.getChildrenCount();
                    int maxNum = (int)allNum;
                    int randomNum = new Random().nextInt(maxNum);
                    Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> ids = ds.iterator();
                    Map<String, Object> newPost = (Map<String, Object>) ids.next().getValue();
                    cevap = newPost.get("Cevap").toString();
                    metiin=newPost.get("Metin").toString();
                    Bundle dataSend=new Bundle();
                    intent.putExtra("CEVAP",cevap);
                    intent.putExtra("Metin",metiin);
                    intent.putExtras(dataSend);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            /*Random random=new Random();
            String item=stringliste.get(random.nextInt(stringliste.size()));
            if(item=="1")
            {
                String umut=getString(R.string.Umut);
                intent.putExtra("CEVAP","UMUT");
                intent.putExtra("Metin",umut);
            }
            else if(item=="2")
            {
                String ozlem=getString(R.string.Özlem);
                intent.putExtra("CEVAP","ÖZLEM");
                intent.putExtra("Metin",ozlem);
            }
            else if(item=="3")
            {
                String ask=getString(R.string.Aşk);
                intent.putExtra("CEVAP","AŞK");
                intent.putExtra("Metin",ask);
            }
            else if(item=="4")
            {
                String merak=getString(R.string.Merak);
                intent.putExtra("CEVAP","MERAK");
                intent.putExtra("Metin",merak);
            }
            else if(item=="5")
            {
                String ozguven=getString(R.string.Özgüven);
                intent.putExtra("CEVAP","ÖZGÜVEN");
                intent.putExtra("Metin",ozguven);
            }
            else
            {
                String sefkat=getString(R.string.Şefkat);
                intent.putExtra("CEVAP","ŞEFKAT");
                intent.putExtra("Metin",sefkat);
            }*/

        }
    }
    private void showQuestion(int i) {
        if(index<totalQuestion) {
            thisQuestion++;
            txtQuestion.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;
            if (Common.questionsList.get(i).getIsImageQuestion() == "true") {
                Picasso.with(getBaseContext()).load(Common.questionsList.get(i).getQuestion()).into(imageView);
                imageView.setVisibility(View.VISIBLE);
                image_text.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            } else {
                question_text.setText(Common.questionsList.get(i).getQuestion());
                imageView.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);

            }
            // if(Common.questionsList.get(i).getAnswerF(""))
            if(Common.questionsList.get(i).getAnswerA().equals(""))
                btnA.setVisibility(View.INVISIBLE);
            else{
                btnA.setVisibility(View.VISIBLE);
                btnA.setText(Common.questionsList.get(i).getAnswerA());}


            if(Common.questionsList.get(i).getAnswerB().equals(""))
                btnB.setVisibility(View.INVISIBLE);
            else{
                btnB.setVisibility(View.VISIBLE);
                btnB.setText(Common.questionsList.get(i).getAnswerB());}


            if(Common.questionsList.get(i).getAnswerC().equals(""))
                btnC.setVisibility(View.INVISIBLE);
            else{
                btnC.setVisibility(View.VISIBLE);
                btnC.setText(Common.questionsList.get(i).getAnswerC());}


            if(Common.questionsList.get(i).getAnswerD().equals(""))
                btnD.setVisibility(View.INVISIBLE);
            else{
                btnD.setVisibility(View.VISIBLE);
                btnD.setText(Common.questionsList.get(i).getAnswerD());}


            if(Common.questionsList.get(i).getAnswerE().equals(""))
                btnE.setVisibility(View.INVISIBLE);
            else{
                btnE.setVisibility(View.VISIBLE);
                btnE.setText(Common.questionsList.get(i).getAnswerE());}


            if(Common.questionsList.get(i).getAnswerF().equals(""))
                btnF.setVisibility(View.INVISIBLE);
            else{
                btnF.setVisibility(View.VISIBLE);
                btnF.setText(Common.questionsList.get(i).getAnswerF());}
        }
        else
        {
            final Intent intent=new Intent(this,BackHome.class);

            FirebaseDatabase database;
            DatabaseReference reference;
            database=FirebaseDatabase.getInstance();
            reference=database.getReference("AnswerCategory");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long allNum = dataSnapshot.getChildrenCount();
                    int maxNum = (int)allNum;
                    int randomNum = new Random().nextInt(maxNum);
                    Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> ids = ds.iterator();
                    Map<String, Object> newPost = (Map<String, Object>) ids.next().getValue();
                    cevap = newPost.get("Cevap").toString();
                    metiin=newPost.get("Metin").toString();
                    Bundle dataSend=new Bundle();
                    intent.putExtra("CEVAP",cevap);
                    intent.putExtra("Metin",metiin);
                    intent.putExtras(dataSend);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            /*Random random=new Random();
            String item=stringliste.get(random.nextInt(stringliste.size()));
            if(item=="1")
            {
                String umut=getString(R.string.Umut);
                intent.putExtra("CEVAP","UMUT");
                intent.putExtra("Metin",umut);
            }
            else if(item=="2")
            {
                String ozlem=getString(R.string.Özlem);
                intent.putExtra("CEVAP","ÖZLEM");
                intent.putExtra("Metin",ozlem);
            }
            else if(item=="3")
            {
                String ask=getString(R.string.Aşk);
                intent.putExtra("CEVAP","AŞK");
                intent.putExtra("Metin",ask);
            }
            else if(item=="4")
            {
                String merak=getString(R.string.Merak);
                intent.putExtra("CEVAP","MERAK");
                intent.putExtra("Metin",merak);
            }
            else if(item=="5")
            {
                String ozguven=getString(R.string.Özgüven);
                intent.putExtra("CEVAP","ÖZGÜVEN");
                intent.putExtra("Metin",ozguven);
            }
            else
            {
                String sefkat=getString(R.string.Şefkat);
                intent.putExtra("CEVAP","ŞEFKAT");
                intent.putExtra("Metin",sefkat);
            }*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalQuestion=Common.questionsList.size();
        //mCountDown=new CountDownTimer(TIMEOUT,INTERVAL) {
            //@Override
            //public void onTick(long mini) {
          //      progressBar.setProgress(progressValue);
            //    progressValue++;
          //  }

            //@Override
            //public void onFinish() {
                //mCountDown.cancel();
                //showQuestion(++index,clickedButton);
           // }
        //};
        showQuestion(index);
    }
}
