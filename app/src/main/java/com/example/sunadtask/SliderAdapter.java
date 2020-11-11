package com.example.sunadtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{
    @NonNull
    private List<Slider> sliders;
    private RecyclerView viewPager2;

    SliderAdapter(@NonNull List<Slider> sliders, RecyclerView viewPager2) {
        this.sliders = sliders;
        this.viewPager2 = viewPager2;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
holder.setImageView(sliders.get(position));
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_slide);

        }
        void setImageView(Slider slider){
            imageView.setImageResource(slider.getImage());
        }
    }
}
