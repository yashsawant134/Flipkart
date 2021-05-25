package com.example.flipkart.ui.search;

public class SearchResultModel {

    String image,name,mrp,id;

    public SearchResultModel(String image, String name, String mrp,String id) {
        this.image = image;
        this.name = name;
        this.mrp = mrp;
        this.id=id;
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

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
