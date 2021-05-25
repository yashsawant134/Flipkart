package com.example.flipkart.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flipkart.R;

import java.util.ArrayList;
import java.util.List;

public class TopDeals_Adapter extends BaseAdapter {

    List<TopDealsGridModel> list =new ArrayList<>();
    int p;
    int type;

    public TopDeals_Adapter(List<TopDealsGridModel> list,int type) {
        this.list = list;
        this.type=type;
    }

    @Override
    public int getCount() {
        if(type==1) {
            return 4;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        p=position;

        if(convertView==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_item,null);

            ImageView productImage=view.findViewById(R.id.top_deals_product_image);
            TextView title=view.findViewById(R.id.top_deals_product_title);
            TextView offer=view.findViewById(R.id.top_deals_product_offers);



            Glide.with(view.getContext()).load(list.get(position).getProductimage()).into(productImage);
            title.setText(list.get(position).getDealname());
            offer.setText(list.get(position).getOffer());

        }
        else {
            view=convertView;
        }

        return view;
    }
}
