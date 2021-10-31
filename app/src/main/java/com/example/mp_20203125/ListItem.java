package com.example.mp_20203125;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ListItem {
    Bitmap bitmapImage;
    Drawable drawableImage;
    String name;

    public void setBitmapImage(Bitmap img) {
        this.bitmapImage = img;
    }
    public void setDrawableImage(Drawable img) {
        this.drawableImage = img;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmapImage() {
        return this.bitmapImage;
    }
    public Drawable getDrawableImage() {
        return this.drawableImage;
    }
    public String getName() {
        return this.name;
    }
}
