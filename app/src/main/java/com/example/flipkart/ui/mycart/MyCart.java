package com.example.flipkart.ui.mycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flipkart.R;
import com.example.flipkart.ui.addAddress.AddAddress;
import com.example.flipkart.ui.home.HomeAllLayoutRecyclerViewModel;
import com.example.flipkart.ui.home.HomeFragment;
import com.example.flipkart.ui.home.MiddleOffersModel;
import com.example.flipkart.ui.home.OffersModel;
import com.example.flipkart.ui.home.TopDealsGridModel;
import com.example.flipkart.ui.home.top_nav_category_model;
import com.example.flipkart.ui.search.SearchResultActivity;
import com.example.flipkart.ui.search.SearchResultAdapter;
import com.example.flipkart.ui.search.SearchResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCart extends AppCompatActivity {
    int x = 0;
    LinearLayout cartEmpty,cartLinear;
    LinearLayout removeCartItem;
    RecyclerView mycartRacyclerView;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String userid;

    TextView cartaddress,cartname;
    AppCompatButton change;

    ArrayList<String> productsId = new ArrayList<>();
    ArrayList<MyCartModel> cartModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Cart");

        cartaddress=findViewById(R.id.cartaddress);
        cartname=findViewById(R.id.cart_username);
        change=findViewById(R.id.cart_change_address);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userid = firebaseUser.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();




        firebaseFirestore.collection("users").document(userid).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size()>0){
                    change.setText("Change");
                    QuerySnapshot documentSnapshot=task.getResult();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cartname.setText("Deliver to :"+document.get("name").toString()+", "+document.get("pincode").toString());
                        cartaddress.setText(document.get("address").toString());
                    }


                    change.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyCart.this, AddAddress.class));
                        }
                    });


                }else{

                    change.setText("ADD");

                    change.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyCart.this, AddAddress.class));

                        }
                    });

                }
            }
        });

















        cartEmpty=findViewById(R.id.cart_is_empty);
        cartLinear=findViewById(R.id.cartlinear);




        mycartRacyclerView = findViewById(R.id.my_cart_recyclerview);


        cartModelArrayList = new ArrayList<>();




        readData(new MyCallback() {


            @Override
            public void onCallback() {

                show();
            }
        });


    }

    public interface MyCallback {
        void onCallback();
    }


    public void show() {



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mycartRacyclerView.setLayoutManager(layoutManager);
        MyCartAdapter cartAdapter = new MyCartAdapter(cartModelArrayList);
        mycartRacyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    }


    public void readData(final MyCallback myCallback) {


        firebaseFirestore.collection("users").document(userid).collection("cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                productsId.add(documentSnapshot.getId());
                            }
                            check(myCallback);
                        }


                    }
                });


    }




    public void check(final MyCallback myCallback) {

        if(productsId.isEmpty()){
            cartEmpty.setVisibility(View.VISIBLE);
            cartLinear.setVisibility(View.INVISIBLE);
        }

        x = 0;
        for (int i = 0; i < productsId.size(); i++) {
            firebaseFirestore.collection("products").document(productsId.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (task.isSuccessful()) {
                        cartModelArrayList.add(new MyCartModel(documentSnapshot.get("product_name").toString(), "dsad", documentSnapshot.get("product_image_1").toString(),productsId.get(x)));
                    }
                    if (x == (productsId.size() - 1)) {
                        myCallback.onCallback();
                    }
                    x++;
                }
            });


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_cart_icon_menu, menu);
        return true;

    }
}
