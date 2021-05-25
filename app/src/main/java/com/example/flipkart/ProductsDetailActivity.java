package com.example.flipkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flipkart.ui.buynow.BuyNow;
import com.example.flipkart.ui.home.HomeAllLayoutRecyclerViewModel;
import com.example.flipkart.ui.home.HomeFragment;
import com.example.flipkart.ui.home.MiddleOffersModel;
import com.example.flipkart.ui.home.OffersModel;
import com.example.flipkart.ui.home.TopDealsGridModel;
import com.example.flipkart.ui.home.top_nav_category_model;
import com.example.flipkart.ui.login.LoginActivity;
import com.example.flipkart.ui.mycart.MyCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ProductsDetailActivity extends AppCompatActivity {

    ViewPager productSliderImageViewpager, zoomProductImages, view_all_details_viewPager;
    TabLayout productSliderindicator, productDetailsTabLayout;
    Boolean whislistselected = false;

    FloatingActionButton AddToWishListIcon;
    CardView seeAllImages;
    LinearLayout linear_layout_of_ProductDetails_scrollView, zoom_product_images_linear_layout, view_all_details_tablinearlayout, add_to_cart,address,Buynow;

    Boolean isScrollVisible = true;
    Button change_address_button, view_all_details;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> images;

    TextView product_name, product_mrp,delevierpin;
    String id, uerid;

    FirebaseUser firebaseUser;

    Map<String, Object> cartMap = new HashMap<>();
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_detail);


        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        delevierpin=findViewById(R.id.delivertopincode);

        id = getIntent().getStringExtra("id");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null) {
            uerid = firebaseUser.getUid();
        }

        product_name = findViewById(R.id.product_name);
        product_mrp = findViewById(R.id.product_mrp);

        firebaseFirestore = FirebaseFirestore.getInstance();


        add_to_cart = findViewById(R.id.add_to_cart_button);
        productSliderImageViewpager = findViewById(R.id.product_images_viewpager);
        productSliderindicator = findViewById(R.id.product_images_slider_indicator);


        images = new ArrayList<>();


        AddToWishListIcon = findViewById(R.id.add_to_wishlist);
        AddToWishListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!whislistselected) {
                    AddToWishListIcon.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    whislistselected = true;
                } else {
                    AddToWishListIcon.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#757474")));
                    whislistselected = false;
                }
            }
        });


        zoom_product_images_linear_layout = findViewById(R.id.zoom_product_images_linear_layout);
        linear_layout_of_ProductDetails_scrollView = findViewById(R.id.linear_layout_of_ProductDetails_scrollView);


        seeAllImages = findViewById(R.id.seeAllImages);

        seeAllImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideScrollView();
            }
        });




        view_all_details = findViewById(R.id.view_all_details);
        view_all_details_tablinearlayout = findViewById(R.id.view_all_details_tablayout);
        view_all_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_all_details_tablinearlayout.setVisibility(View.VISIBLE);
            }
        });


        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        view_all_details_viewPager = findViewById(R.id.view_all_details_viewpager);

        ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount());
        view_all_details_viewPager.setAdapter(productDetailsAdapter);

        view_all_details_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_all_details_viewPager.setCurrentItem(tab.getPosition(), true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cartMap.put("cartItem", id);
                if(firebaseUser!=null) {

                    firebaseFirestore.collection("users").document(uerid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                firebaseFirestore.collection("users").document(uerid).collection("cart").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult();

                                        if (documentSnapshot.exists()) {

                                        } else {
                                            firebaseFirestore.collection("users").document(uerid).collection("cart").document(id).set(cartMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            startActivity(new Intent(ProductsDetailActivity.this, MyCart.class));
                                                        }
                                                    });

                                        }
                                    }
                                });


                            }
                        }
                    });
                }else{
                    startActivity(new Intent(ProductsDetailActivity.this, LoginActivity.class));

                }


            }
            

        });

        Buynow=findViewById(R.id.buynow);

        Buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firebaseUser!=null) {

                    String name = product_name.getText().toString();
                    String image = images.get(0);
                    Intent intent = new Intent(ProductsDetailActivity.this, BuyNow.class);
                    intent.putExtra("price", price);
                    intent.putExtra("productName", name);
                    intent.putExtra("image", image);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(ProductsDetailActivity.this, LoginActivity.class));

                }
            }
        });


        readData(new MyCallback() {


            @Override
            public void onCallback() {

                show();
            }
        });





        /////check user has address
        TextView deliverpin=findViewById(R.id.delivertopincode);
         address=findViewById(R.id.address);
        change_address_button = findViewById(R.id.change_address_button);
        if(firebaseUser!=null) {
            firebaseFirestore.collection("users").document(uerid).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.getResult().size() > 0) {
                        for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                            delevierpin.setText(documentSnapshot.get("pincode").toString());
                        }

                        change_address_button.setText("Change Address");
                        change_address_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SelectAddress selectAddressfragment = new SelectAddress();
                                selectAddressfragment.show(getSupportFragmentManager(), selectAddressfragment.getTag());
                            }
                        });
                    } else {
                        change_address_button.setText("Add Address");
                        deliverpin.setVisibility(View.GONE);


                        change_address_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SelectAddress selectAddressfragment = new SelectAddress();
                                selectAddressfragment.show(getSupportFragmentManager(), selectAddressfragment.getTag());
                            }
                        });

                    }
                }
            });
        }else{
            change_address_button.setText("Login");
            deliverpin.setVisibility(View.GONE);


            change_address_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProductsDetailActivity.this, LoginActivity.class));
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_cart_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isScrollVisible) {
                finish();
            } else {
                linear_layout_of_ProductDetails_scrollView.setVisibility(View.VISIBLE);
                zoom_product_images_linear_layout.setVisibility(View.GONE);
                isScrollVisible = true;
            }

            return true;
        }
        if (item.getItemId() == R.id.toolbar_cart) {
            if(firebaseUser!=null) {
                startActivity(new Intent(ProductsDetailActivity.this, MyCart.class));
                return true;
            }else{
                startActivity(new Intent(ProductsDetailActivity.this, LoginActivity.class));
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void hideScrollView() {
        linear_layout_of_ProductDetails_scrollView.setVisibility(View.GONE);
        isScrollVisible = false;
        zoom_product_images_linear_layout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (isScrollVisible) {
            finish();
        } else {
            linear_layout_of_ProductDetails_scrollView.setVisibility(View.VISIBLE);
            zoom_product_images_linear_layout.setVisibility(View.GONE);
            isScrollVisible = true;
        }
    }


    public void show() {


        productSliderindicator.setupWithViewPager(productSliderImageViewpager);

        ProductImagesViewPagerAdapter productImagesViewPagerAdapter = new ProductImagesViewPagerAdapter(images);
        productSliderImageViewpager.setAdapter(productImagesViewPagerAdapter);


        zoomProductImages = findViewById(R.id.zoom_product_images_viewpager);
        zoomProductImages.setAdapter(productImagesViewPagerAdapter);
    }


    public interface MyCallback {
        void onCallback();
    }


    public void readData(final MyCallback myCallback) {


        firebaseFirestore.collection("products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long i = 1; i <= documentSnapshot.getLong("number_of_images"); i++) {
                        images.add(documentSnapshot.get("product_image_" + i).toString());
                    }
                    product_mrp.setText("Rs " + documentSnapshot.get("product_price").toString());
                    product_name.setText(documentSnapshot.get("product_name").toString());
                    myCallback.onCallback();
                    price=documentSnapshot.get("product_price").toString();
                }
            }
        });


    }
}