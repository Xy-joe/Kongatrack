package com.lightedcode.kongalite.models;

/**
 * Created by joebuntu on 3/15/17.
 */

public class Promo {
    String name, image, description;
    int price, discount;

    @Override
    public String toString() {
        return "Promo{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }

    /**  public Promo(String description, int discount, String image, String name, int price) {
     this.description = description;
     this.discount = discount;
     this.image = image;
     this.name = name;
     this.price = price;
     }**/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
