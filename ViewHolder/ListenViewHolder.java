package com.example.joousope.bakdinle.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joousope.bakdinle.R;
import com.example.joousope.bakdinle.SongInfo;

public class ListenViewHolder extends RecyclerView.ViewHolder {
    public Button btnAction;
    public TextView songName;
    public TextView artistName;
    public TextView songUrl;
    private ListenViewHolder.ClickListener mClickListener;
    public ListenViewHolder(@NonNull View itemView) {
        super(itemView);
        songName=(TextView)itemView.findViewById(R.id.songName);
        artistName=(TextView)itemView.findViewById(R.id.artistName);
        //songUrl=(TextView)itemView.findViewById(R.id.songUrl);
        //btnAction=(Button)itemView.findViewById(R.id.btnAction);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

    }
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ListenViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }



}
