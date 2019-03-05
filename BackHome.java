package com.example.joousope.bakdinle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Model.Questions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class BackHome extends AppCompatActivity {
    Button btnDifferent;
    TextView txtCevap,txtMetin;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    String mesajDagit;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_home);
        txtCevap=(TextView)findViewById(R.id.txtCevap);
        txtMetin=(TextView)findViewById(R.id.txtMetin);
        progressBar=(ProgressBar)findViewById(R.id.backProgressBar);
        btnDifferent=(Button)findViewById(R.id.btnDifferent);
        intent=getIntent();
        Bundle extra=intent.getExtras();
        if(extra!=null)
        {
            String cevap=extra.getString("CEVAP");
            String metin=extra.getString("Metin");
            mesajDagit=cevap;
            txtCevap.setText(Objects.requireNonNull(extra.getCharSequence("CEVAP")).toString());
            txtMetin.setText(Objects.requireNonNull(extra.getCharSequence("Metin")).toString());
        }
        btnDifferent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BackHome.this,SongListen.class);
                Bundle dataSend=new Bundle();
                intent.putExtra("CEVAP",mesajDagit);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
                //Fragment fragment=MusicPlayerFragment.newInstance();

            }
        });

    }
}
