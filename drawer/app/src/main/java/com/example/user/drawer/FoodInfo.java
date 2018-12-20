package com.example.user.drawer;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class FoodInfo {

    public int image;
    public String name;

    public ImageView imageView;
    public Bitmap bitmap;

    public FoodInfo(){

    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
    private boolean isFav;

    public FoodInfo(int image, String name, boolean flag){
        this.image = image;
        this.name = name;
        this.isFav = flag;
    }


    public FoodInfo(String name, ImageView imageView){
        this.name = name;
        this.imageView = imageView;
    }

    public FoodInfo(String name, Bitmap bitmap){
        this.name = name;
        this.bitmap = bitmap;
    }

    public FoodInfo(String name, int image){
        this.name = name;
        this.image = image;
    }


    public FoodInfo(String name){
        this.name = name;
    }


    public FoodInfo(String name, boolean flag){
        this.name = name;
        this.isFav = flag;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name= name;
    }

    public int getImage(){return image;}

    public void setImage(int image){this.image=image;}

}
