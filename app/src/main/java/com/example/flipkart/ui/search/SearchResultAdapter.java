package com.example.flipkart.ui.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.R;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    ArrayList<SearchResultModel> list;

    public SearchResultAdapter(ArrayList<SearchResultModel> list) {
        this.list = list;
    }

    @NonNull

    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchResultAdapter.ViewHolder holder, final int position) {

        final String name=list.get(position).getName();
        String mrp=list.get(position).getMrp();
        String image=list.get(position).getImage();

        holder.setData(name,mrp,image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),com.example.flipkart.ProductsDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("name",name);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,mrp;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            mrp=itemView.findViewById(R.id.product_mrp);
            image=itemView.findViewById(R.id.product_image);

        }
        private void setData(String nam,String price, String img){
            name.setText(nam);
            mrp.setText(price);
            Glide.with(itemView.getContext()).load(img).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(image);
        }


    }
}
