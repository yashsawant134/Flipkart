package com.example.flipkart.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.flipkart.MainActivity1;
import com.example.flipkart.R;
import com.example.flipkart.ui.mycart.MyCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ArrayList<String>product_id;
    RecyclerView search_result_recycler_view;
    FirebaseFirestore firebaseFirestore;
    int x=0;
    ArrayList<SearchResultModel> searchResultList;
    ProgressBar progressBar;
    LinearLayout no_product_found;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("searchString"));


        firebaseFirestore=FirebaseFirestore.getInstance();

        product_id=(ArrayList<String>) getIntent().getSerializableExtra("products_id");

        no_product_found=findViewById(R.id.no_product_found);

        if(product_id.isEmpty()){
            no_product_found.setVisibility(View.VISIBLE);
        }




        search_result_recycler_view=findViewById(R.id.search_result_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        search_result_recycler_view.setLayoutManager(layoutManager);

        searchResultList=new ArrayList<>();


        progressBar=findViewById(R.id.progressBarsearch);
        progressBar.setVisibility(View.VISIBLE);
        readData(new MyCallback() {

            @Override
            public void onCallback() {
                show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_cart_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
        finish();

            return true;
        }

        if(item.getItemId()==R.id.toolbar_cart){
            startActivity(new Intent(SearchResultActivity.this, MyCart.class));
            return true;
        }
        if(item.getItemId()==R.id.search){
            startActivity(new Intent(SearchResultActivity.this, SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }



    public void show() {
        progressBar.setVisibility(View.GONE);
    SearchResultAdapter searchResultAdapter=new SearchResultAdapter(searchResultList);
    search_result_recycler_view.setAdapter(searchResultAdapter);
    searchResultAdapter.notifyDataSetChanged();

    }


    public interface MyCallback {
        void onCallback();
    }


    public void readData(final MyCallback myCallback) {



        x=0;
        for (int i = 0; i < product_id.size(); i++) {
            firebaseFirestore.collection("products").document(product_id.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(task.isSuccessful()) {
                        searchResultList.add(new SearchResultModel(documentSnapshot.get("product_image_1").toString(), documentSnapshot.get("product_name").toString(),"₹ "+ documentSnapshot.get("product_price").toString(),documentSnapshot.getId()));
                    }
                    if(x==(product_id.size()-1)){
                        myCallback.onCallback();
                    }
                    x++;
                }
            });


        }


    }

}