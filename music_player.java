package com.example.joousope.bakdinle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.joousope.bakdinle.Common.SongCommon;

import java.io.IOException;
import java.util.Objects;

public class music_player extends AppCompatActivity implements View.OnClickListener{
    AppCompatImageButton buttonPlay,buttonNext,buttonBack,buttonStop;
    SeekBar seekbar;
    MediaPlayer mediaPlayer;
    private int position=0;
    //String position;
    Intent intent;
    Handler handler=new Handler();
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        buttonPlay=(AppCompatImageButton)findViewById(R.id.playMusic);
        buttonNext=(AppCompatImageButton)findViewById(R.id.backMusic);
        buttonBack=(AppCompatImageButton)findViewById(R.id.forwardMusic);
        buttonStop=(AppCompatImageButton)findViewById(R.id.stopMusic);
        buttonPlay.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        intent=getIntent();
        Bundle extra=intent.getExtras();
        /*if(extra!=null)
        {
            String position2=extra.getString("position");
            int song=Integer.parseInt(position2);

        }*/
    }

    @Override
    public void onClick(final View v) {

                switch (v.getId()) {
                    case R.id.playMusic:
                        if (mediaPlayer == null)
                            if(position<SongCommon.songList.size())
                                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(SongCommon.songList.get(position).getSongUrl()));
                            else
                                mediaPlayer=MediaPlayer.create(getApplicationContext(),Uri.parse(SongCommon.songList.get(0).getSongUrl()));
                        mediaPlayer.start();
                        buttonPlay.setVisibility(View.INVISIBLE);
                        buttonStop.setVisibility(View.VISIBLE);
                        //Thread t=new Thread();
                        //t.start();
                        break;
                    case R.id.forwardMusic:
                        position++;
                        if(mediaPlayer!=null)
                            mediaPlayer.stop();
                        //uri = Uri.parse(link[position + 1]);
                        if(position<SongCommon.songList.size())
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(SongCommon.songList.get(position).getSongUrl()));
                        else
                            mediaPlayer=mediaPlayer.create(getApplicationContext(),Uri.parse(SongCommon.songList.get(0).getSongUrl()));
                        //mediaPlayer.setDataSource(getApplicationContext(), uri);
                        mediaPlayer.start();
                       // seekbar.setProgress(0);
                        //seekbar.setMax(mediaPlayer.getDuration());
                        buttonPlay.setVisibility(View.INVISIBLE);
                        buttonStop.setVisibility(View.VISIBLE);
                        //t = new Thread();
                        //t.start();
                        break;
                    case R.id.backMusic:
                        position--;
                        if(mediaPlayer!=null)
                            mediaPlayer.stop();
                        if(position==-1)
                            position=SongCommon.songList.size();
                        //uri = Uri.parse(link[position + 1]);
                        if(position<SongCommon.songList.size())
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(SongCommon.songList.get(position).getSongUrl()));
                        else
                            mediaPlayer=mediaPlayer.create(getApplicationContext(),Uri.parse(SongCommon.songList.get(0).getSongUrl()));
                        //mediaPlayer.setDataSource(getApplicationContext(), uri);
                        mediaPlayer.start();
                       // seekbar.setProgress(0);
                        //seekbar.setMax(mediaPlayer.getDuration());
                        buttonPlay.setVisibility(View.INVISIBLE);
                        buttonStop.setVisibility(View.VISIBLE);
                        break;
                    case R.id.stopMusic:
                        if(mediaPlayer!=null)
                            mediaPlayer.pause();
                        mediaPlayer=null;
                        buttonPlay.setVisibility(View.VISIBLE);
                        buttonStop.setVisibility(View.INVISIBLE);
                }
    }
    public class MyThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                if(mediaPlayer!=null){  seekbar.setProgress(mediaPlayer.getCurrentPosition());}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
