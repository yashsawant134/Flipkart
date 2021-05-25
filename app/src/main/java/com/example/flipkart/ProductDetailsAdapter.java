package com.example.flipkart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    int tabCount;
    public ProductDetailsAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm, tabCount);
        this.tabCount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProductDescription();
            case 1:
                return new ProductSpecification();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
