package com.example.flipkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.flipkart.ui.home.TopDealsGridModel;
import com.example.flipkart.ui.home.TopDeals_Adapter;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewAllProductActivity extends AppCompatActivity  {

    ArrayList<TopDealsGridModel> gridlist;
    GridView top_deals_gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_product);

         getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle=new Bundle();

        gridlist=(ArrayList<TopDealsGridModel>) getIntent().getSerializableExtra("gridlist");




        top_deals_gridview=findViewById(R.id.top_deals_grid_view);
        TopDeals_Adapter topDeals_adapter=new TopDeals_Adapter(gridlist,0);
        top_deals_gridview.setAdapter(topDeals_adapter);
        topDeals_adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_cart_icon_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==android.R.id.home){
            finish();
            return true;
       }
       return super.onOptionsItemSelected(item);
    }
}