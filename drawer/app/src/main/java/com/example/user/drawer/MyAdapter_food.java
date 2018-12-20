package com.example.user.drawer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter_food extends RecyclerView.Adapter<MyAdapter_food.ItemViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference grilled = database.getReference("grilled_kor_name");

    private ArrayList<FoodInfo> myData;

    public MyAdapter_food(ArrayList<FoodInfo> myData){
        this.myData = myData;
    }

    // create new view holder
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_food, parent, false);
        return new ItemViewHolder(v);
    }

    // transform content of View to that poistion data.
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        holder.name.setText(myData.get(position).name);

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView name;
        TextView eng_name;

        public ItemViewHolder(View view){
            super(view);
            //image = view.findViewById(R.id.food_image);
            name = view.findViewById(R.id.name);
        }

    }

    // return the size of dataset.
    @Override
    public int getItemCount() {
        return myData.size();
    }

}
