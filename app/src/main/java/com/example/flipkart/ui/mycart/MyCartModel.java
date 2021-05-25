package com.example.flipkart.ui.mycart;

public class MyCartModel {

    String Product_name,short_info;
    String image;
    String id;

    public MyCartModel(String product_name, String short_info, String image,String id) {
        Product_name = product_name;
        this.short_info = short_info;
        this.image = image;
        this.id=id;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getShort_info() {
        return short_info;
    }

    public void setShort_info(String short_info) {
        this.short_info = short_info;
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
