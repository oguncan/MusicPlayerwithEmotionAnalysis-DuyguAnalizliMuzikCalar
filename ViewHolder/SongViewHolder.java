package com.example.joousope.bakdinle.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joousope.bakdinle.Interface.ItemClickListener;
import com.example.joousope.bakdinle.R;

public class SongViewHolder extends RecyclerView.ViewHolder{

    public AppCompatImageButton btnAction;
    public TextView songName;
    public TextView artistName;
    public TextView songUrl;

    //private ItemClickListener itemClickListener;
    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        songName=(TextView)itemView.findViewById(R.id.songName);
        artistName=(TextView)itemView.findViewById(R.id.artistName);
        //songUrl=(TextView)itemView.findViewById(R.id.songUrl);
        //btnAction=(AppCompatImageButton) itemView.findViewById(R.id.btnAction);
        //itemView.setOnClickListener(this);
    }


}
