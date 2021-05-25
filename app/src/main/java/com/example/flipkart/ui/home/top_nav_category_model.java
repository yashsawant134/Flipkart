package com.example.flipkart.ui.home;

public class top_nav_category_model {

    String image;
    String name;

    public top_nav_category_model(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
