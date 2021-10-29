package com.example.mp_20203125;

import android.graphics.drawable.Drawable;

public class ListItem {
    Drawable image;
    String name;

    public void setImage(Drawable img) {
        this.image = img;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return this.image;
    }
    public String getName() {
        return this.name;
    }
}
