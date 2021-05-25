package com.example.flipkart.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.R;

import java.util.ArrayList;
import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    public OffersAdapter(List<OffersModel> list) {
        this.list = list;
    }

    List<OffersModel> list=new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_recycleview_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String heading=list.get(position).getHeading();
        String deal=list.get(position).getDeal();
        String compliment=list.get(position).getCompliment();

        String image=list.get(position).getImage();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), list.get(position).getId(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.setData(heading,deal,compliment,image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView heading,deal,compliment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heading=itemView.findViewById(R.id.offers_heading);
            image=itemView.findViewById(R.id.offer_image);
            deal=itemView.findViewById(R.id.offers_item_deal);
            compliment=itemView.findViewById(R.id.offers_compliment);
        }

        private void setData(String head,String d,String comp,String img){
            heading.setText(head);
            deal.setText(d);
            compliment.setText(comp);
            Glide.with(itemView.getContext()).load(img).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(image);

        }
    }
}
