package com.lightedcode.kongalite.models;

/**
 * Created by joebuntu on 3/13/17.
 */

public class Category {
    String names, price, image;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category(String names, String price, String image) {

        this.names = names;
        this.price = price;
        this.image = image;
    }
}
