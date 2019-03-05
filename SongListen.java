package com.example.joousope.bakdinle;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.joousope.bakdinle.Common.SongCommon;
import com.example.joousope.bakdinle.Interface.ItemClickListener;
import com.example.joousope.bakdinle.Interface.OnClickListenerYeni;
import com.example.joousope.bakdinle.Interface.OnItemClickListener;
import com.example.joousope.bakdinle.Model.Fragment.Song;
import com.example.joousope.bakdinle.ViewHolder.SongViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.joousope.bakdinle.Common.SongCommon.SongCategoryId;

public class SongListen extends AppCompatActivity implements OnItemClickListener {

    Context context;
    ArrayList<SongInfo> songs;
    OnItemClickListener onItemClickListener;
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    Button btnAction;
    TextView songName;
    TextView songUrl;
    MediaPlayer mediaPlayer;
    TextView artistName;
    String mesajDagit;
    FirebaseDatabase database;
    DatabaseReference reference;
    private RecyclerView.LayoutManager layoutManager;
    int songsize=0;
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    FirebaseRecyclerAdapter adapter;
    public SongListen()
    {

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }
    public SongListen(Context context, ArrayList<SongInfo> songs)
    {
        this.songs=songs;
        this.context=context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_listen);
        //btnAction=(Button)findViewById(R.id.btnAction);
        songAdapter=new SongAdapter(SongListen.this,songs);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerSong);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Songs");
        Intent intent=getIntent();
        Bundle extra=intent.getExtras();
        if(extra!=null) {
            String cevap = extra.getString("CEVAP");
            mesajDagit = cevap;
            loadAnswer(mesajDagit);
        }
        else
        {
            loadSongs(SongCommon.SongCategoryId);
        }
        loadSong();
        //yenile();
        //btnAction.setOnClickListener(this);
    }

    private void loadAnswer(String songAnswer) {
        if(SongCommon.songList.size()>0)
            SongCommon.songList.clear();

        reference.orderByChild("SongAnswer").equalTo(songAnswer).addValueEventListener(new ValueEventListener() {
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

    private void loadSongs(String SongCategoryId) {
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

    private void loadSong() {
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database=FirebaseDatabase.getInstance();
        adapter=new FirebaseRecyclerAdapter<SongInfo, SongViewHolder>(SongInfo.class,R.layout.layout_deneme,SongViewHolder.class,reference)
        {
            @Override
            protected void populateViewHolder(final SongViewHolder viewHolder, SongInfo model, final int position)
            {
                viewHolder.artistName.setText(model.getArtistName());
                viewHolder.songName.setText(model.getSongName());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(SongListen.this,music_player.class)
                                .putExtra("position",position);
                        startActivity(intent);
                        finish();
                    }
                });
                /*viewHolder.btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        /*try {
                            //final SongInfo c=SongCommon.songList.get(viewHolder.getAdapterPosition());
                            //onItemClickListener.onItemClick(viewHolder.btnAction,v,c,(position));
                            if (viewHolder.btnAction.equals()) {
                                viewHolder.btnAction.setText("Play");
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            } else {
                                AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,20,0);
                                mediaPlayer = new MediaPlayer();
                                //mediaPlayer=MediaPlayer.create(SongListen.this,Uri.parse(SongCommon.songList.get(position).getSongUrl()));
                                mediaPlayer.start();
                                //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                //mediaPlayer.setAudioStreamType(AudioManager.AUDIO_SESSION_ID_GENERATE);
                                //mediaPlayer.setDataSource(obj.getSongUrl());
                                mediaPlayer.setDataSource(SongCommon.songList.get(viewHolder.getAdapterPosition()).getSongUrl());
                                //FileInputStream fileInputStream=new FileInputStream(str);
                                //mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/bakdinle-840ae.appspot.com/o/Avril%20Lavigne%20-%20Knockin%20on%20Heavens%20Door.mp3?alt=media&token=75c2b549-8f3e-4971-b896-529f7b84da8c");
                                //fileInputStream.close();
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        //mp=MediaPlayer.create(SongListen.this,Uri.parse(SongCommon.songList.get(position).getSongUrl()));
                                        mp.start();
                                        viewHolder.btnAction.setText("Stop");
                                    }
                                });
                                mediaPlayer.prepare();
                            }
                        }
                        catch(IOException e)
                        {
                        }
                        adapter.getRef(viewHolder.getAdapterPosition()).getKey();

                    }
                })*/

            }
            @Override
            public int getItemCount() {
                return SongCommon.songList.size();
            }

            public SongInfo getItem(int adapterPosition){
                return SongCommon.songList.get(adapterPosition);
            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);



    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(Button b, View v, SongInfo obj, int position) {

    }
}
