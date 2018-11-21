package com.example.user.drawer;

public class FoodInfo {

    public String image;
    public String name;

    public FoodInfo(){

    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
    private boolean isFav;

    public FoodInfo(String image, String name, boolean flag){
        this.image = image;
        this.name = name;
        this.isFav = flag;
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

    public String getImage(){return image;}

    public void setImage(String image){this.image=image;}

}
