package com.example.user.drawer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drawer.databinding.ListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.user.drawer.R.layout.list_food;


public class FoodList extends AppCompatActivity{

    //firebase instance
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //recycle view
    private ListFoodBinding foodBinding;
    private RecyclerView.Adapter adapter;
    ArrayList<FoodInfo> myData = new ArrayList<>();

    //test

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.list_food);
        foodBinding = DataBindingUtil.setContentView(this, list_food);

        TextView food_type = (TextView) findViewById(R.id.category_name);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        food_type.setText(type);

        setRecyclerView();

        switch (type){
            case "밥" : setData_rice();break;
            case "상차림" : setData_table();break;
            case "찜" : setData_steamed();break;
            case "구이" : setData_grilled();break;
            case "전골" : setData_stew();break;
            case "조림" : setData_boiled();break;
            case "전" : setData_pancake();break;
            case "떡" : setData_ricecake();break;
            case "만두" : setData_dumpling();break;
            case "국" : setData_soup();break;
        }

    }

    private void setRecyclerView(){
        final Intent intent_food = new Intent(this, Dialog.class);

        foodBinding.foodRecyclerView.setHasFixedSize(true);
        adapter = new MyAdapter_food(myData);
        foodBinding.foodRecyclerView.setAdapter(adapter);
        foodBinding.foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodBinding.foodRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), foodBinding.foodRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    public void onItemClick(View view, int position, MotionEvent e){

                        View child = foodBinding.foodRecyclerView.findChildViewUnder(e.getX(), e.getY());

                        if(child != null){
                            TextView name = foodBinding.foodRecyclerView.getChildViewHolder(child).itemView.findViewById(R.id.name);
                            intent_food.putExtra("detail", name.getText());
                            startActivity(intent_food);
                        }
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), position+"번 째 아이템 클릭", Toast.LENGTH_SHORT).show();
                    }
                })
        );

    }


    public void setData_rice(){
        DatabaseReference food_name = database.getReference("rice_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }


    public void setData_table(){
        DatabaseReference food_name = database.getReference("table_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_steamed(){
        DatabaseReference food_name = database.getReference("steamed_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_grilled(){
        DatabaseReference food_name = database.getReference("grilled_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_stew(){
        DatabaseReference food_name = database.getReference("stew_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_boiled(){
        DatabaseReference food_name = database.getReference("boiled_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_pancake(){
        DatabaseReference food_name = database.getReference("pancake_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_ricecake(){
        DatabaseReference food_name = database.getReference("ricecake_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_dumpling(){
        DatabaseReference food_name = database.getReference("dumpling_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    public void setData_soup(){
        DatabaseReference food_name = database.getReference("soup_kor_name");
        food_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()){
                    String value = shot.getValue().toString();
                    myData.add(new FoodInfo(value));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

}