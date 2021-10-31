package com.example.mp_20203125;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    ArrayList<ListItem> list = new ArrayList<>();

    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, parent, false);
        }

        ImageView img = view.findViewById(R.id.i1) ;
        TextView name = view.findViewById(R.id.n1) ;

        ListItem item = list.get(pos);
        if(item.getBitmapImage() == null){
            img.setImageDrawable(item.getDrawableImage());
        }else{
            img.setImageBitmap(item.getBitmapImage());
        }
        name.setText(item.getName());

        return view;
    }
    public void addBitmapItem(Bitmap img, String name) {
        ListItem item = new ListItem();

        item.setBitmapImage(img);
        item.setName(name);

        list.add(item);
    }
    public void addDrawableItem(Drawable img, String name) {
        ListItem item = new ListItem();

        item.setDrawableImage(img);
        item.setName(name);

        list.add(item);
    }

    public void deleteItem(int pos){
        ListItem item = list.get(pos);
        list.remove(item);
    }

}
