package com.example.flipkart.ui.home;

import android.os.Parcelable;

import java.io.Serializable;

public class TopDealsGridModel implements Serializable {

    String dealname,offer,title;
    String productimage;


    public TopDealsGridModel(String dealname, String offer, String productimage,String title) {
        this.dealname = dealname;
        this.offer = offer;
        this.productimage = productimage;
        this.title=title;

    }

    public String getDealname() {
        return dealname;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
