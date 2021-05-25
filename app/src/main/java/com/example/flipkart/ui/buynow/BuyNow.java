package com.example.flipkart.ui.buynow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.R;
import com.example.flipkart.SelectAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BuyNow extends AppCompatActivity {

    TextView product_name,product_price,product_bottom_price,quantity,pricedetailsprice,pricedetailstotal,username,useraddress,userphonenumber;
    AppCompatButton increase,decrease,changeaddressbtn;
    ImageView product_image;
    int qty=1;
    Intent i=getIntent();

    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null) {
            userid = firebaseUser.getUid();
        }
        firebaseFirestore=FirebaseFirestore.getInstance();



        product_name=findViewById(R.id.product_name);
        product_price=findViewById(R.id.product_price);
        product_bottom_price=findViewById(R.id.total_price);
        increase=findViewById(R.id.quantity_increase);
        decrease=findViewById(R.id.quantity_decrease);
        product_image=findViewById(R.id.buy_image);
        quantity=findViewById(R.id.quantity);
        pricedetailsprice=findViewById(R.id.pricedetails_price);
        pricedetailstotal=findViewById(R.id.pricedetailstotalprice);
        username=findViewById(R.id.buynowname);
        useraddress=findViewById(R.id.buynowaddress);
        userphonenumber=findViewById(R.id.buynowphone);
        changeaddressbtn=findViewById(R.id.change_address_button_buynow);


        String mrp=getIntent().getStringExtra("price");
        int price=Integer.valueOf(mrp);

        getSupportActionBar().setTitle("Order Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        product_name.setText(getIntent().getStringExtra("productName"));
        product_price.setText(getIntent().getStringExtra("price"));
        Glide.with(this).load(getIntent().getStringExtra("image")).apply(new RequestOptions().placeholder(R.drawable.flipkartlogo)).into(product_image);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty>=1 && qty<10) {
                    qty++;
                    quantity.setText(String.valueOf(qty));
                    int p = price* qty;
                    product_price.setText("Rs. "+String.valueOf(p));
                    product_bottom_price.setText("Rs. "+String.valueOf(p));
                    pricedetailsprice.setText("Rs. "+String.valueOf(p));
                    pricedetailsprice.setText("Rs. "+String.valueOf(p-500));
                }
            }
        });



        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty>1 && qty<=10) {
                    qty--;
                    quantity.setText(String.valueOf(qty));
                    int p = price* qty;
                    product_price.setText("Rs. "+String.valueOf(p));
                    product_bottom_price.setText("Rs. "+String.valueOf(p));
                    pricedetailsprice.setText("Rs. "+String.valueOf(p));
                    pricedetailsprice.setText("Rs. "+String.valueOf(p-500));
                }
            }
        });


        firebaseFirestore.collection("users").document(userid).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        useraddress.setText(documentSnapshot.get("address").toString());
                        username.setText(documentSnapshot.get("name").toString());
                    }
                }
            }
        });

        firebaseFirestore.collection("users").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    userphonenumber.setText(documentSnapshot.get("phone_number").toString());
                }
            }
        });


        changeaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAddress selectAddressfragment = new SelectAddress();
                selectAddressfragment.show(getSupportFragmentManager(), selectAddressfragment.getTag());
            }
        });
    }
}