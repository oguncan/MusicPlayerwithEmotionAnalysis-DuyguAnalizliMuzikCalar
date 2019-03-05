package com.example.joousope.bakdinle;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.joousope.bakdinle.Model.User;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String KEY_USER="KEY_USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectFragment = null;
                switch(menuItem.getItemId())
                {
                    case R.id.action_questions:
                        selectFragment=questionFragment.newInstance();
                        break;
                    case R.id.action_music_player:
                        selectFragment=MusicPlayerFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,selectFragment);
                transaction.commit();
                return true;
            }
        });
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,questionFragment.newInstance());
        transaction.commit();
    }

}
