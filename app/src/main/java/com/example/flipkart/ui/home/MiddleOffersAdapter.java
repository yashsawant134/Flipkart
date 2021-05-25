package com.example.flipkart.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiddleOffersAdapter extends RecyclerView.Adapter<MiddleOffersAdapter.ViewHolder> {
    List<MiddleOffersModel> list=new ArrayList<>();

    public MiddleOffersAdapter(List<MiddleOffersModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.middle_offers_layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       String productimg=list.get(position).getImage();
       String title =list.get(position).getTitle();
       String name=list.get(position).getProduct_name();
       String companyname=list.get(position).getProduct_company();
       String save=list.get(position).getSaveupto();

       holder.setData(productimg,title,name,save,companyname);

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView title,name,companyName,saveupto;
        LinearLayout middle_offers_bg_color;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.middle_offers_product_image);
            title=itemView.findViewById(R.id.middle_offers_title);
            name=itemView.findViewById(R.id.Middle_offers_product_name);
            saveupto=itemView.findViewById(R.id.middle_offers_price);
            companyName=itemView.findViewById(R.id.middel_offers_company_name);
            middle_offers_bg_color=itemView.findViewById(R.id.middel_offers_background_color);
        }

        private void setData(String img,String titl,String nam,String save,String compname){

            String[] color={"#FAD8E3","#DDEFC8","#FFCDF6FB","#FFFAF5C9"};
            Random random=new Random();
            String c=color[random.nextInt(4)];

            middle_offers_bg_color.setBackgroundColor(Color.parseColor(c));
            Glide.with(itemView.getContext()).load(img).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(productImage);
            title.setText(titl);
            name.setText(nam);
            saveupto.setText(save);
            companyName.setText(compname);
        }
    }
}
