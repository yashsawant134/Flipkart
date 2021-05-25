package com.example.flipkart.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.R;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class top_nav_category_adapter extends RecyclerView.Adapter<top_nav_category_adapter.ViewHolder> {



   List<top_nav_category_model> category_model_list=new ArrayList<>();

    public top_nav_category_adapter(ArrayList<top_nav_category_model> category_model_list) {
        this.category_model_list = category_model_list;
    }

    @NonNull
    @Override
    public top_nav_category_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.top_nav_category_items_for_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull top_nav_category_adapter.ViewHolder holder, int position) {

        String image=category_model_list.get(position).getImage();
        String name=category_model_list.get(position).getName();

        Picasso.get().load(image).placeholder(R.drawable.flipkartlogo).into(holder.image);

//        Glide.with(holder.image.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(holder.image);

        holder.setDataoftopcategory(image,name);
    }

    @Override
    public int getItemCount() {
        return category_model_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.top_nav_category_image);
            name=itemView.findViewById(R.id.top_nav_category_name);
        }

        private void setDataoftopcategory(String imgURL, final String text){



            name.setText(text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),text,Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
