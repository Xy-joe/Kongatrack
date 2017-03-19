package com.lightedcode.kongalite.models;

/**
 * Created by joebuntu on 3/13/17.
 */

public class Content {
    int img;
    String description;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Content(int img, String description) {

        this.img = img;
        this.description = description;
    }
}
