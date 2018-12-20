package com.example.user.drawer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchPageAdapter extends RecyclerView.Adapter<SearchPageAdapter.ItemViewHolder> implements Filterable {

    private ArrayList<FoodInfo> myDataset;
    private ArrayList<FoodInfo> myDatasetFiltered;
    private com.example.user.drawer.myClickListener myClickListener;
    private Context context;
    private SearchPageAdapterListener listener;

    //
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public ImageView imgFav;

        public ItemViewHolder(View view){
            super(view);
//            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            imgFav = (ImageView) view.findViewById(R.id.imageFav);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(myDatasetFiltered.get(getAdapterPosition()));
                }
            });
        }

    }
    public SearchPageAdapter(ArrayList<FoodInfo> myDataset, com.example.user.drawer.myClickListener clickListener,
                             Context context, SearchPageAdapterListener listener){
        this.myDataset = myDataset;
        this.myDatasetFiltered = myDataset;
        this.myClickListener = clickListener;
        this.context = context;
        this.listener = listener;
    }

    // create new view holder
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.page_search_row, parent, false);
        return new ItemViewHolder(v);
    }

    // transform content of View to that poistion data.
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final FoodInfo foodInfo = myDatasetFiltered.get(position);
        holder.name.setText(foodInfo.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Dialog.class);
                intent.putExtra("detail",holder.name.getText().toString() );
                context.startActivity(intent);
            }
        });

        holder.imgFav.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myClickListener.onItemClick(foodInfo, position);
            }
        });

        if(foodInfo.isFav()){
            holder.imgFav.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_favorite_black_24dp));
        }else{
            holder.imgFav.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_favorite_white_24dp));
        }




    }

    // return the size of dataset.
    @Override
    public int getItemCount() {
        return myDatasetFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    myDatasetFiltered = myDataset;
                } else {
                    ArrayList<FoodInfo> filtered = new ArrayList<>();
                    for (FoodInfo row : myDataset) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filtered.add(row);
                        }
                    }
                    myDatasetFiltered = filtered;
                }

                FilterResults results = new FilterResults();
                results.values = myDatasetFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                myDatasetFiltered = (ArrayList<FoodInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    interface SearchPageAdapterListener {
        void onSelected(FoodInfo item);
    }

}
