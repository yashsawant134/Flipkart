package com.example.flipkart.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flipkart.R;
import com.example.flipkart.SpeechRecognition;
import com.example.flipkart.ui.mycart.MyCart;
import com.example.flipkart.ui.search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.Empty;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.android.gms.common.util.CollectionUtils.isEmpty;


public class HomeFragment extends Fragment {


    FirebaseFirestore firebaseFirestore;
//    StorageReference storageReference;
//    FirebaseStorage firebaseStorage;


    RecyclerView HomeAllLayoutRecyclerView;
    String[] bannerImages;
    ArrayList<OffersModel> offersModellist;

    ArrayList<MiddleOffersModel> middleOffersList;
    ArrayList<TopDealsGridModel> top_deals_list;
    ArrayList<TopDealsGridModel> todays_fashion_deal_list;
    ArrayList<top_nav_category_model> top_nav_category_model_list;
    String adlink;
    ArrayList<HomeAllLayoutRecyclerViewModel> homeAllLayoutRecyclerViewModelslist = new ArrayList<>();
    HomeAllLayoutRecyclerViewAdapter homeAllLayoutRecyclerViewAdapter;

    ProgressBar progressBar;
    EditText searchbar;


    LinearLayout mic;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mic=view.findViewById(R.id.mic_voice);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechRecognition speechRecognition = new SpeechRecognition();
                speechRecognition.show(getChildFragmentManager(), speechRecognition.getTag());
            }
        });

        progressBar = view.findViewById(R.id.progressbar);

        searchbar = view.findViewById(R.id.search_bar);
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);

                startActivity(intent);


            }
        });



        HomeAllLayoutRecyclerView = view.findViewById(R.id.home_all_layout_recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();

        top_nav_category_model_list = new ArrayList<>();//        TOP NAVIGATION CATEGORY START


        bannerImages = new String[5];//        HOME BANNER SLIDER START


        offersModellist = new ArrayList<>();//////////////////Offers Slider


        middleOffersList = new ArrayList<>(); ///////Middle Offers Start


        todays_fashion_deal_list = new ArrayList<>();   ////////Todays FAshion Deals Start


        LinearLayoutManager home_all_layout_manager = new LinearLayoutManager(getActivity());
        home_all_layout_manager.setOrientation(LinearLayoutManager.VERTICAL);

        HomeAllLayoutRecyclerView.setLayoutManager(home_all_layout_manager);


        readData(new MyCallback() {


            @Override
            public void onCallback() {
                progressBar.setVisibility(View.GONE);
                show();
            }
        });


        return view;

    }


    public void show() {


//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(2,adlink));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(3, offersModellist, ""));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(4, top_deals_list, "Top Deals", "#9FD1FA", "#FF1F84D5", "#ffffff"));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(2,adlink));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(5, middleOffersList, 1));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(5, middleOffersList, 1));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(4, todays_fashion_deal_list, "Today's Fashion Deal", "#F4286D", "#ffffff", "#000000"));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(2,adlink));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(3, offersModellist, ""));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(5, middleOffersList, 1));
//        homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(4, todays_fashion_deal_list, "Top Brands", "#C27DCD", "#ffffff", "#000000"));


        homeAllLayoutRecyclerViewAdapter = new HomeAllLayoutRecyclerViewAdapter(homeAllLayoutRecyclerViewModelslist);

        HomeAllLayoutRecyclerView.setAdapter(homeAllLayoutRecyclerViewAdapter);


        homeAllLayoutRecyclerViewAdapter.notifyDataSetChanged();

    }


    public interface MyCallback {
        void onCallback();
    }


    public void readData(final MyCallback myCallback) {

        progressBar.setVisibility(View.VISIBLE);


        firebaseFirestore.collection("categories").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        top_nav_category_model_list.add(new top_nav_category_model(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryname").toString()));
                    }


                }
            }
        });


        firebaseFirestore.collection("home_layout").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(0, top_nav_category_model_list));
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        if ((long) documentSnapshot.get("view_type") == 1) {
                            long noOfBanners = (long) documentSnapshot.get("number_of_banners");
                            for (int i = 1; i <= noOfBanners; i++) {
                                bannerImages[i - 1] = documentSnapshot.get("banner_" + i).toString();
                            }
                            homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(1, bannerImages, 2.34));


                        } else if ((long) documentSnapshot.get("view_type") == 2) {
                            adlink = documentSnapshot.get("stripAd").toString();
                            homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(2, adlink));
                        } else if ((long) documentSnapshot.get("view_type") == 4) {
                            top_deals_list = new ArrayList<>();

                            String btncolor = documentSnapshot.get("button_color").toString();
                            String textColor = documentSnapshot.get("button_text_color").toString();
                            String bgColor = documentSnapshot.get("bg_color").toString();
                            String title = documentSnapshot.get("title").toString();

                            Random random = new Random();

                            List<Integer> isThere = new ArrayList<>();

                            for (long i = 1; i <= (long) documentSnapshot.get("number_of_products"); i++) {


                                top_deals_list.add(new TopDealsGridModel((String) documentSnapshot.get("product_name_" + i), (String) documentSnapshot.get("offers_" + i), (String) documentSnapshot.get("image_" + i), (String) title));


                            }
                            homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(4, top_deals_list, title, (String) documentSnapshot.get("bg_color"), (String) documentSnapshot.get("button_color"), (String) documentSnapshot.get("button_text_color")));

                        } else if ((long) documentSnapshot.get("view_type") == 5) {

                            for (long i = 1; i <= (long) documentSnapshot.get("number_of_products"); i++) {
                                middleOffersList.add(new MiddleOffersModel(documentSnapshot.get("title_" + i).toString(), documentSnapshot.get("product_name_" + i).toString(), documentSnapshot.get("product_company_" + 1).toString(), documentSnapshot.get("mrp_" + i).toString(), documentSnapshot.get("product_image_" + i).toString()));
                            }
                            homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(5, middleOffersList, 1));
                        } else if ((long) documentSnapshot.get("view_type") == 3) {
                            for (long i = 1; i <= (long) documentSnapshot.get("number_of_products"); i++) {
                                offersModellist.add(new OffersModel(documentSnapshot.get("title_" + i).toString(), documentSnapshot.get("offer_name_" + i).toString(), documentSnapshot.get("deal_name_" + i).toString(), documentSnapshot.get("image_" + i).toString(), documentSnapshot.getId()));
                            }

                            homeAllLayoutRecyclerViewModelslist.add(new HomeAllLayoutRecyclerViewModel(3, offersModellist, ""));
                        }
                    }


                    myCallback.onCallback();

                }

            }
        });


    }


}