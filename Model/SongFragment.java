package com.example.joousope.bakdinle.Model;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.joousope.bakdinle.R;
import com.example.joousope.bakdinle.SongInfo;

import java.util.ArrayList;

public class SongFragment extends Fragment {
    LayoutInflater myFragment;
    private ArrayList<SongInfo> songList;
    private ListView songView;
    View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songList=new ArrayList<SongInfo>();
        songView=(ListView)view.findViewById(R.id.action_music_player);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getActivity()).inflate(R.layout.layout_music_player,container,false);
        //myFragment=LayoutInflater.from(getActivity(),container,false);
        return view;
    }
}
