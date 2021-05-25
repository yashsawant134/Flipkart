package com.example.flipkart.ui.home;

import java.util.ArrayList;
import java.util.List;

public class HomeAllLayoutRecyclerViewModel {
    public static final int Top_navigation_category=0;
    public static final int Home_Slider_Banner=1;
    public static final int Strinp_ad=2;
    public static final int Offers_Slider=3;
    public static final int GridView=4;
    public static final int MiddleOffers=5;

    private int type;



    //////////Top_navigation Category Start

    private ArrayList topNavCategoryModelList;

    public HomeAllLayoutRecyclerViewModel(int type, ArrayList topNavCategoryModelList) {
        if(type==0) {

            this.type = type;
            this.topNavCategoryModelList = topNavCategoryModelList;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<top_nav_category_model> getTopNavCategoryModelList() {
        return topNavCategoryModelList;
    }

    public void setTopNavCategoryModelList(ArrayList<top_nav_category_model> topNavCategoryModelList) {
        this.topNavCategoryModelList = topNavCategoryModelList;
    }
    //////////Top_navigation Category End





    ///Home Sider Banner


    String[] images;

    public HomeAllLayoutRecyclerViewModel(int type, String[] images,Double a) {
        this.type = type;
        this.images = images;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    ///Home Sider BannerEnd





    ////Strip Add start

String StripAdimage;


    public HomeAllLayoutRecyclerViewModel(int type, String StrinpAdimage) {
        this.type = type;
        this.StripAdimage=StrinpAdimage;
    }

    public String getStripAdimage() {
        return StripAdimage;
    }

    public void setStripAdimage(String stripAdimage) {
        StripAdimage = stripAdimage;
    }


    ////Strip Add start End






    /////Offers Slider Start


    ArrayList<OffersModel> offersModelArrayList;

    public HomeAllLayoutRecyclerViewModel(int type, ArrayList<OffersModel> offersModelArrayList,String position) {
        this.type = type;
        this.offersModelArrayList = offersModelArrayList;
    }


    public ArrayList<OffersModel> getOffersModelArrayList() {
        return offersModelArrayList;
    }

    public void setOffersModelArrayList(ArrayList<OffersModel> offersModelArrayList) {
        this.offersModelArrayList = offersModelArrayList;
    }


    /////Offers Slider End





    ///Top Deal grid Start

    ArrayList<TopDealsGridModel> gridmodelist;
    String title,backgroundColor,buttonColor,buttonTextColor;

    public HomeAllLayoutRecyclerViewModel(int type, ArrayList<TopDealsGridModel> gridmodelist, String title, String backgroundColor, String buttonColor, String buttonTextColor) {
        this.type = type;
        this.gridmodelist = gridmodelist;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.buttonColor = buttonColor;
        this.buttonTextColor = buttonTextColor;
    }

    public static int getTop_navigation_category() {
        return Top_navigation_category;
    }

    public ArrayList<TopDealsGridModel> getGridmodelist() {
        return gridmodelist;
    }

    public void setGridmodelist(ArrayList<TopDealsGridModel> gridmodelist) {
        this.gridmodelist = gridmodelist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    public String getButtonTextColor() {
        return buttonTextColor;
    }

    public void setButtonTextColor(String buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }
    ///Top Deal grid end




    ///////////Middle Offers start

    ArrayList<MiddleOffersModel> middleOffersModelArrayList;

    public HomeAllLayoutRecyclerViewModel(int type, ArrayList<MiddleOffersModel> middleOffersModelArrayList,int p) {
        this.type = type;
        this.middleOffersModelArrayList = middleOffersModelArrayList;
    }

    public ArrayList<MiddleOffersModel> getMiddleOffersModelArrayList() {
        return middleOffersModelArrayList;
    }

    public void setMiddleOffersModelArrayList(ArrayList<MiddleOffersModel> middleOffersModelArrayList) {
        this.middleOffersModelArrayList = middleOffersModelArrayList;
    }

    ///////////Middle Offers End


}
