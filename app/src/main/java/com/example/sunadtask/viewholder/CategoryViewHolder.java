package com.example.sunadtask.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunadtask.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView t1;
    public ImageView i1;


    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        t1=(TextView)itemView.findViewById(R.id.name);
        i1=(ImageView) itemView.findViewById(R.id.image);
    }
}
