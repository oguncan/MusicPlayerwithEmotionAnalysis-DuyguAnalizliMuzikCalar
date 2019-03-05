package com.example.joousope.bakdinle.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joousope.bakdinle.Interface.ItemClickListener;
import com.example.joousope.bakdinle.R;

public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Name;
    public ImageView Image;
    private ItemClickListener itemClickListener;
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        Image=(ImageView)itemView.findViewById(R.id.question_image);
        Name=(TextView)itemView.findViewById(R.id.question_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

}
