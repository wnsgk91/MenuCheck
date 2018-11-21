package com.example.user.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FavoriteListAdapter extends BaseAdapter {

    private List<FoodInfo> foods;
    private Context context;
    private TextView favorite_food_name;

    public FavoriteListAdapter(List foods, Context context){
        this.foods = foods;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.foods.size();
    }

    @Override
    public Object getItem(int position) {
        return this.foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.favorite_list_row, null);
            favorite_food_name = (TextView)convertView.findViewById(R.id.favorite_food_name);
        }
        favorite_food_name.setText(foods.get(position).getName());
        return convertView;
    }

}
