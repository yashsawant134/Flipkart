package com.example.flipkart.ui.home;

public class OffersModel {

    String heading,deal,compliment;
    String image;
    String id;

    public OffersModel(String heading, String deal, String compliment, String image,String id) {
        this.heading = heading;
        this.deal = deal;
        this.compliment = compliment;
        this.image = image;
        this.id=id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getCompliment() {
        return compliment;
    }

    public void setCompliment(String compliment) {
        this.compliment = compliment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
