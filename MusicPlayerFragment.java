package com.example.joousope.bakdinle;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.joousope.bakdinle.Common.Common;
import com.example.joousope.bakdinle.Common.SongCommon;

import com.example.joousope.bakdinle.Interface.ItemClickListener;
import com.example.joousope.bakdinle.Interface.OnItemClickListener;
import com.example.joousope.bakdinle.Model.Fragment.Song;
import com.example.joousope.bakdinle.ViewHolder.MusicViewHolder;
import com.example.joousope.bakdinle.ViewHolder.SongViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class MusicPlayerFragment extends Fragment {
    View myFragment;
    RecyclerView recyclerView;
    SeekBar seekBar;
    Button btnAction;
    SongAdapter songAdapter;
    FirebaseRecyclerAdapter<Song,MusicViewHolder> adapter;
    //FirebaseRecyclerAdapter<SongInfo,SongViewHolder> adapter;
    ArrayList<Song> songs=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    MediaPlayer mediaPlayer;
    private Uri musicUri;

    DatabaseReference databaseReference;
    FirebaseDatabase database;


    public MusicPlayerFragment(){}
    public static MusicPlayerFragment newInstance()
    {
        MusicPlayerFragment musicPlayerFragment=new MusicPlayerFragment();
        return musicPlayerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("SongCategory");
        //databaseReference=database.getReference("Songs");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle savedInstanceState) {
        /*myFragment=layoutInflater.inflate(R.layout.layou_music_playerr,viewGroup,false);
        return myFragment;*/
        View itemView= layoutInflater.inflate(R.layout.layou_music_playerr,viewGroup,false);
       // View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layou_music_playerr,viewGroup,false);
        //loadSongs(SongCommon.CategoryId);
        recyclerView=(RecyclerView)itemView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        //btnAction=(Button)itemView.findViewById(R.id.btnAction);
        layoutManager=(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
       // DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

       // recyclerView.addItemDecoration(new SpacesItemDecoration());
        loadMusic();
        return itemView;
    }
    public class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView artistName;
        TextView songUrl;
        Button btnAction;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            //songUrl=(TextView)itemView.findViewById(R.id.songUrl);
            songName=(TextView)itemView.findViewById(R.id.songName);
            artistName=(TextView)itemView.findViewById(R.id.artistName);
            //btnAction=(Button)itemView.findViewById(R.id.btnAction);
        }
    }
    private void loadMusic() {
        adapter=new FirebaseRecyclerAdapter<Song, MusicViewHolder>(Song.class,R.layout.layout_music_adapter,MusicViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(MusicViewHolder viewHolder, Song model, int position) {
                viewHolder.Name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.Image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                            Intent intent=new Intent(getActivity(),SongListen.class);
                            SongCommon.SongCategoryId=adapter.getRef(position).getKey();
                            startActivity(intent);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        /*songAdapter=new SongAdapter(getActivity(),songs);
        musicUri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/bakdinle-840ae.appspot.com/o/Avril%20Lavigne%20-%20Knockin%20on%20Heavens%20Door.mp3?alt=media&token=75c2b549-8f3e-4971-b896-529f7b84da8c");
        SongInfo s =new SongInfo("Evanescence","Lithium",musicUri.toString());
        songs.add(s);
        songAdapter.setOnItemClickListener(new adapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Button b, View v, SongInfo obj, int position) {
                try {
                    if (b.getText().toString().equals("Stop")) {
                        b.setText("Play");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    } else {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        /*mediaPlayer.setDataSource(obj.getSongUrl());
                        mediaPlayer.prepareAsync();
                        FileInputStream fileInputStream=new FileInputStream(obj.getSongUrl());
                        mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/bakdinle-840ae.appspot.com/o/Avril%20Lavigne%20-%20Knockin%20on%20Heavens%20Door.mp3?alt=media&token=75c2b549-8f3e-4971-b896-529f7b84da8c");
                        fileInputStream.close();
                        mediaPlayer.prepare();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                                b.setText("Stop");

                            }
                        });
                    }
                }
                catch(IOException e)
                {
                }
            }
        });*/
    }
}
