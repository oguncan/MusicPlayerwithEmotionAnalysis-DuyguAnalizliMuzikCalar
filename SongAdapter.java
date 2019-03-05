package com.example.joousope.bakdinle;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Common.SongCommon;
import com.example.joousope.bakdinle.Interface.ItemClickListener;
import com.example.joousope.bakdinle.Interface.OnItemClickListener;
import com.example.joousope.bakdinle.ViewHolder.SongViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;

import static com.example.joousope.bakdinle.Common.SongCommon.SongCategoryId;
import static com.example.joousope.bakdinle.Common.SongCommon.songList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private Context context;
    OnItemClickListener onItemClickListener;
    private List<SongInfo> songs;
    ItemClickListener ıtemClickListener;
    private LayoutInflater layoutInflater;
    MediaPlayer mediaPlayer;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    public SongAdapter(Context context, List<SongInfo> list)
    {
        this.context=context;
        this.songs= list;
    }
    public interface OnItemClickListener{
        void onItemClick(Button b,View v,SongInfo obj, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_deneme,viewGroup,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongViewHolder songViewHolder, final int i) {
        final SongInfo c=songs.get(i);
        songViewHolder.position=i;
        songViewHolder.songName.setText(songs.get(i).getSongName());
        songViewHolder.artistName.setText(songs.get(i).getArtistName());
        //songViewHolder.songUrl.setText(songs.get(i).getSongUrl());
        /*songViewHolder.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(songViewHolder.buttonPlay,v,c,i);
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView songName,artistName,songUrl;
        public Button buttonPlay;
        public int position=0;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songName=(TextView)itemView.findViewById(R.id.songName);
            artistName=(TextView)itemView.findViewById(R.id.artistName);
            //songUrl=(TextView)itemView.findViewById(R.id.songUrl);
            //buttonPlay=(Button)itemView.findViewById(R.id.btnAction);
        }
    }
    public void dinleClick(View v)
    {
        MediaPlayer mediaPlayer=new MediaPlayer();
        try{
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/bakdinle-840ae.appspot.com/o/Avril%20Lavigne%20-%20Knockin%20on%20Heavens%20Door.mp3?alt=media&token=75c2b549-8f3e-4971-b896-529f7b84da8c");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

