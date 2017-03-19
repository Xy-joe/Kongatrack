package com.lightedcode.kongalite.models;

/**
 * Created by joebuntu on 3/13/17.
 */

public class ListCategory {
    String names;
    int price;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public ListCategory(String names, int price) {

        this.names = names;
        this.price = price;

    }
}
