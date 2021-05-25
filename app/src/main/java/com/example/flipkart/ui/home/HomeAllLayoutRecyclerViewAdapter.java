package com.example.flipkart.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flipkart.ProductsDetailActivity;
import com.example.flipkart.R;
import com.example.flipkart.ViewAllProductActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeAllLayoutRecyclerViewAdapter extends RecyclerView.Adapter {


    List<HomeAllLayoutRecyclerViewModel> homeAllLayoutRecyclerViewModelList = new ArrayList<>();


    public HomeAllLayoutRecyclerViewAdapter(List<HomeAllLayoutRecyclerViewModel> homeAllLayoutRecyclerViewModelList) {
        this.homeAllLayoutRecyclerViewModelList = homeAllLayoutRecyclerViewModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homeAllLayoutRecyclerViewModelList.get(position).getType()) {
            case 0:
                return HomeAllLayoutRecyclerViewModel.Top_navigation_category;
            case 1:
                return HomeAllLayoutRecyclerViewModel.Home_Slider_Banner;
            case 2:
                return HomeAllLayoutRecyclerViewModel.Strinp_ad;

            case 3:
                return HomeAllLayoutRecyclerViewModel.Offers_Slider;

            case 4:
                return HomeAllLayoutRecyclerViewModel.GridView;

            case 5:
                return HomeAllLayoutRecyclerViewModel.MiddleOffers;
            default:
                return -1;
        }
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeAllLayoutRecyclerViewModel.Top_navigation_category:
                View top_nav_cat_recycler = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_navigation_category_recycler_view, parent, false);
                return new TopNavigationCategory(top_nav_cat_recycler);


            case HomeAllLayoutRecyclerViewModel.Home_Slider_Banner:
                View home_slider_banner = LayoutInflater.from((parent.getContext())).inflate(R.layout.home_slider_banner, parent, false);
                return new HomeSliderBanner(home_slider_banner);

            case HomeAllLayoutRecyclerViewModel.Strinp_ad:
                View stripad = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_banner_ad, parent, false);
                return new StripAd(stripad);


            case HomeAllLayoutRecyclerViewModel.Offers_Slider:
                View offerSlider = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_recycler_view, parent, false);
                return new OfferSlider(offerSlider);

            case HomeAllLayoutRecyclerViewModel.GridView:
                View girdlayout=LayoutInflater.from(parent.getContext()).inflate(R.layout.top_delas_grid_layout,parent,false);
                return new Gridlayout(girdlayout);


            case HomeAllLayoutRecyclerViewModel.MiddleOffers:
                View middleOfferslayout=LayoutInflater.from(parent.getContext()).inflate(R.layout.middle_offers_layout,parent,false);
                return new MiddleOffersClass(middleOfferslayout);

            default:
                return null;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        switch (homeAllLayoutRecyclerViewModelList.get(position).getType()) {
            case HomeAllLayoutRecyclerViewModel.Top_navigation_category:
                final ArrayList<top_nav_category_model> top_nav_category_modelList = homeAllLayoutRecyclerViewModelList.get(position).getTopNavCategoryModelList();


                ((TopNavigationCategory) holder).setDataOfTopNavCategory(top_nav_category_modelList,position);


                break;

            case HomeAllLayoutRecyclerViewModel.Home_Slider_Banner:
                String[] images = homeAllLayoutRecyclerViewModelList.get(position).getImages();
                ((HomeSliderBanner) holder).setDataOfHomeSliderBanner(images);
                break;

            case HomeAllLayoutRecyclerViewModel.Strinp_ad:
                String image=homeAllLayoutRecyclerViewModelList.get(position).getStripAdimage();
                ((StripAd)holder).setStripAd(image);

                break;

            case HomeAllLayoutRecyclerViewModel.Offers_Slider:
                    ArrayList<OffersModel> offersModelslist=homeAllLayoutRecyclerViewModelList.get(position).getOffersModelArrayList();
                ((OfferSlider)holder).setDataOfOfferSlider(offersModelslist);
                break;

                case HomeAllLayoutRecyclerViewModel.GridView:
                    ArrayList<TopDealsGridModel> gridModelslist=homeAllLayoutRecyclerViewModelList.get(position).getGridmodelist();
                    String title=homeAllLayoutRecyclerViewModelList.get(position).getTitle();
                    String bgcolor=homeAllLayoutRecyclerViewModelList.get(position).getBackgroundColor();
                    String btnbgcolor=homeAllLayoutRecyclerViewModelList.get(position).getButtonColor();
                    String btnTextColor=homeAllLayoutRecyclerViewModelList.get(position).getButtonTextColor();
                    ((Gridlayout)holder).setDataOfGridlayout(gridModelslist,title,bgcolor,btnbgcolor,btnTextColor);
                    break;

            case HomeAllLayoutRecyclerViewModel.MiddleOffers:
                ArrayList<MiddleOffersModel> middleOffersModelArrayList=homeAllLayoutRecyclerViewModelList.get(position).getMiddleOffersModelArrayList();
                ((MiddleOffersClass)holder).setDataOfMiddleOffers(middleOffersModelArrayList);

            default:
                return;
        }

    }


    @Override
    public int getItemCount() {
        return homeAllLayoutRecyclerViewModelList.size();
    }


    public class TopNavigationCategory extends RecyclerView.ViewHolder {
        private RecyclerView top_cat_recyclerview;

        public TopNavigationCategory(@NonNull View itemView) {
            super(itemView);

            top_cat_recyclerview = itemView.findViewById(R.id.top_cat_recyclerview);

        }

        private void setDataOfTopNavCategory(final ArrayList<top_nav_category_model> top_nav_category_model_list, int position) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            top_cat_recyclerview.setLayoutManager(layoutManager);
            top_nav_category_adapter top_nav_cat_adapter = new top_nav_category_adapter(top_nav_category_model_list);
            top_cat_recyclerview.setAdapter(top_nav_cat_adapter);


            top_nav_cat_adapter.notifyDataSetChanged();
        }

    }


    public class HomeSliderBanner extends RecyclerView.ViewHolder {
        SliderView bannersliderView;

        public HomeSliderBanner(@NonNull View itemView) {
            super(itemView);

            bannersliderView = itemView.findViewById(R.id.home_slider_banner);
        }

        private void setDataOfHomeSliderBanner(String[] images) {
            BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(images);
            bannersliderView.setSliderAdapter(bannerSliderAdapter);
            bannersliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            bannersliderView.startAutoCycle();
        }
    }


    public class StripAd extends RecyclerView.ViewHolder {

        ImageView stripad;
        public StripAd(@NonNull View itemView) {
            super(itemView);
            stripad=itemView.findViewById(R.id.stripad);
        }

        private void setStripAd(String StripAd){
            Glide.with(itemView.getContext()).load(StripAd).into(stripad);
        }
    }


    public class OfferSlider extends RecyclerView.ViewHolder{
        RecyclerView offer_recycler_view;
        public OfferSlider(@NonNull View itemView) {
            super(itemView);

            offer_recycler_view=itemView.findViewById(R.id.offers_recyclerView);


        }

        private void setDataOfOfferSlider(ArrayList<OffersModel> offersModelArrayList){
            LinearLayoutManager layoutManager1=new LinearLayoutManager(itemView.getContext());
            layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

            offer_recycler_view.setLayoutManager(layoutManager1);

            OffersAdapter offersAdapter=new OffersAdapter(offersModelArrayList);
            offer_recycler_view.setAdapter(offersAdapter);

            offersAdapter.notifyDataSetChanged();

        }
    }


    public class Gridlayout extends RecyclerView.ViewHolder  {
        GridView top_deals_gridview;
        TextView title;
        Button viewall;
        ConstraintLayout gridbgcolorlayout;
        public Gridlayout(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.top_delas_title);
            gridbgcolorlayout=itemView.findViewById(R.id.topDealsBgColor);
          top_deals_gridview=itemView.findViewById(R.id.top_deals_grid_view);
            viewall=itemView.findViewById(R.id.top_delas_viewall_btn);

        }

        private void setDataOfGridlayout(final ArrayList<TopDealsGridModel> top_deals_list, final String titl, String bgcolor, String btncolor, String btnTextcolor){
            TopDeals_Adapter topDeals_adapter=new TopDeals_Adapter(top_deals_list,1);
            top_deals_gridview.setAdapter(topDeals_adapter);
            topDeals_adapter.notifyDataSetChanged();

            title.setText(titl);

            viewall.setBackgroundColor(Color.parseColor(btncolor));
            viewall.setTextColor(Color.parseColor(btnTextcolor));
            gridbgcolorlayout.setBackgroundColor(Color.parseColor(bgcolor));
            top_deals_gridview.setClickable(true);

            viewall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewall_product_activity=new Intent(itemView.getContext(), com.example.flipkart.ViewAllProductActivity.class);
                    viewall_product_activity.putExtra("title",titl);
                    viewall_product_activity.putExtra("gridlist",top_deals_list);
                    itemView.getContext().startActivity(viewall_product_activity);
                }
            });

            top_deals_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Intent product=new Intent(itemView.getContext(), ProductsDetailActivity.class);
                    product.putExtra("title",top_deals_list.get(position).dealname);
                    itemView.getContext().startActivity(product);

                }
            });


        }
    }


    public class MiddleOffersClass extends RecyclerView.ViewHolder{

        RecyclerView middle_offer_recyclerview;

        public MiddleOffersClass(@NonNull View itemView) {
            super(itemView);

            middle_offer_recyclerview=itemView.findViewById(R.id.middle_offers_recyclerview);
            LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(itemView.getContext());
            linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
            middle_offer_recyclerview.setLayoutManager(linearLayoutManager3);
        }

        private void setDataOfMiddleOffers(ArrayList<MiddleOffersModel> middleOffersModelArrayList){

            MiddleOffersAdapter middleOffersAdapter=new MiddleOffersAdapter(middleOffersModelArrayList);
            middle_offer_recyclerview.setAdapter(middleOffersAdapter);
            middleOffersAdapter.notifyDataSetChanged();

        }
    }
}
