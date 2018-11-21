package com.example.user.drawer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {


    private ArrayList<FoodInfo> myDataset;
    private myClickListener myClickListener;
    private Context context;

    public MyAdapter(ArrayList<FoodInfo> myDataset, MainActivity mainActivity){
        this.myDataset = myDataset;
        this.context = mainActivity;
        //this.myClickListener = listener;
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
        final FoodInfo foodInfo = myDataset.get(position);
        //holder.image.setText(myDataset.get(position).image);
        holder.name.setText(myDataset.get(position).name);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("!", v.toString() );
                Intent intent = new Intent(v.getContext(), FoodList.class);
                intent.putExtra(holder.name.getText().toString(), "111");
                context.startActivity(intent);
            }
        });
    }


    //
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        //public ImageView imgFav;

        public ItemViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            //imgFav = (ImageView) view.findViewById(R.id.imageFav);
        }

    }

    // return the size of dataset.
    @Override
    public int getItemCount() {
        return myDataset.size();
    }



}
