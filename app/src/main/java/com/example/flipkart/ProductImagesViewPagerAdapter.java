package com.example.flipkart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ProductImagesViewPagerAdapter extends PagerAdapter {

    ArrayList<String> productimages;

    public ProductImagesViewPagerAdapter(ArrayList<String> productimages) {
        this.productimages = productimages;
    }

    @Override
    public int getCount() {
        return productimages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        ImageView productSliderImage=new ImageView(container.getContext());


        Glide.with(container).load(productimages.get(position)).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(productSliderImage);
        container.addView(productSliderImage);
        return productSliderImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }



}
