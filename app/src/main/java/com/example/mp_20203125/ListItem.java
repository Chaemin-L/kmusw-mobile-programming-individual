package com.example.mp_20203125;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.net.URI;

public class ListItem {
    Bitmap image;
    String name;

    public void setImage(Bitmap img) {
        this.image = img;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return this.image;
    }
    public String getName() {
        return this.name;
    }
}
