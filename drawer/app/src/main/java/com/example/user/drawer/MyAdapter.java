package com.example.user.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder>{

    private ArrayList<FoodInfo> myDataset;
    private myClickListener myClickListener;
    private Context context;

    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference image = database.getReference("image");

    Bitmap bitmap;

    public MyAdapter(ArrayList<FoodInfo> myDataset, MainActivity mainActivity){
        this.myDataset = myDataset;
        this.context = mainActivity;
    }

    // create new view holder
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_category, parent, false);
        return new ItemViewHolder(v);
    }

    // transform content of View to that poistion data.
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.name.setText(myDataset.get(position).name);
        holder.image.setImageResource(myDataset.get(position).image);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodList.class);
                intent.putExtra(holder.name.getText().toString(), "111");

                context.startActivity(intent);
            }

        });
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        //public ImageView imgFav;

        public ItemViewHolder(View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
        }

        public ImageView getImage(){
            return this.image;
        }

        public TextView getName(){
            return this.name;
        }

    }

    // return the size of dataset.
    @Override
    public int getItemCount() {
        return myDataset.size();
    }


}
