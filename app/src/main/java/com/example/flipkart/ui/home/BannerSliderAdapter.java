package com.example.flipkart.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class BannerSliderAdapter extends SliderViewAdapter<BannerSliderAdapter.ViewHolder> {

    String[] images;

    public BannerSliderAdapter(String[] images) {
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_banner_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {



        Glide.with(viewHolder.imageView.getContext()).load(images[position]).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.home_slider_banner_item);

        }
    }
}
