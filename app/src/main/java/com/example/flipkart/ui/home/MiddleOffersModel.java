package com.example.flipkart.ui.home;

public class MiddleOffersModel {
    String title,product_name,product_company,saveupto;
    String image;

    public MiddleOffersModel(String title, String product_name, String product_company, String saveupto, String image) {
        this.title = title;
        this.product_name = product_name;
        this.product_company = product_company;
        this.saveupto = saveupto;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_company() {
        return product_company;
    }

    public void setProduct_company(String product_company) {
        this.product_company = product_company;
    }

    public String getSaveupto() {
        return saveupto;
    }

    public void setSaveupto(String saveupto) {
        this.saveupto = saveupto;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
