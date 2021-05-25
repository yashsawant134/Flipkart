package com.example.flipkart.ui.mycart;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    ArrayList<MyCartModel> list;
    LinearLayout RemoveCartItem,cartEmpty,cartLinear;

    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String userid;


    public MyCartAdapter(ArrayList<MyCartModel> list) {
        this.list = list;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userid = firebaseUser.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        String name=list.get(position).getProduct_name();
        String info=list.get(position).getShort_info();
        String image=list.get(position).getImage();
        String id=list.get(position).getId();
        holder.setData(name,info,image);

        holder.RemoveCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("users").document(userid).collection("cart").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemChanged(position);


                        }
                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView product_name,product_short_info;

        LinearLayout RemoveCartItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.cart_image);
            product_name=itemView.findViewById(R.id.cart_product_name);
            product_short_info=itemView.findViewById(R.id.product_short_info);
            RemoveCartItem=itemView.findViewById(R.id.removeCartItem);
            cartEmpty=itemView.findViewById(R.id.cart_is_empty);
            cartLinear=itemView.findViewById(R.id.cartlinear);
        }

        private void setData(String nam,String information,String img){

            Glide.with(itemView.getContext()).load(img).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(image);
            product_name.setText(nam);
            product_short_info.setText(information);
        }
    }
}
