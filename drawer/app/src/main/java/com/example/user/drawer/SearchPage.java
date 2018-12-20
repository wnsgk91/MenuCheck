package com.example.user.drawer;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user.drawer.databinding.PageSearchBinding;
import com.example.user.drawer.db.DBHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.user.drawer.R.layout.page_search;

public class SearchPage extends AppCompatActivity implements SearchPageAdapter.SearchPageAdapterListener{


    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference food_kor_name = database.getReference("food_image");


    private PageSearchBinding mainBinding;
    private SearchPageAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    ArrayList<FoodInfo> myDataset = new ArrayList<>();
    //sqlite
    private DBHelper dbHelper;

    private Toolbar toolbar;
    private SearchView searchView;

    ArrayList<String> name_list = new ArrayList<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, page_search);
        setData();
        toolbar = findViewById(R.id.t1_custom);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    private void setData() {
        myDataset.clear();


        food_kor_name.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dbHelper == null) {
                    dbHelper = new DBHelper(getApplicationContext());
                }
                ArrayList<FoodInfo> info = dbHelper.getAll();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    String value = shot.getKey();
                    name_list.add(value);

                    ArrayList favorite_list = new ArrayList();
                    for (FoodInfo row : info) {
                        favorite_list.add(row.getName());
                    }
                    if (favorite_list.contains(value)) {
                        myDataset.add(new FoodInfo(value, true));
                    } else {
                        myDataset.add(new FoodInfo(value, false));
                    }
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        setRecyclerView();
    }

    private void setRecyclerView() {
        adapter= new SearchPageAdapter(myDataset, new myClickListener() {
            @Override
            public void onItemClick(FoodInfo obj, int pos) {
                if (obj.isFav()) {
                    obj.setFav(false);
                    adapter.notifyDataSetChanged();
                    //SQL lite
                    if (dbHelper == null) {
                        dbHelper = new DBHelper(getApplicationContext());
                    }
                    dbHelper.delFood(obj);
                } else {
                    obj.setFav(true);
                    adapter.notifyDataSetChanged();
                    //SQL lite
                    if (dbHelper == null) {
                        dbHelper = new DBHelper(getApplicationContext());
                    }
                    dbHelper.addFood(obj);
                }
            }

        },SearchPage.this, this);

        mainBinding.myRecyclerView.setHasFixedSize(true);
        mainBinding.myRecyclerView.setAdapter(adapter);
        mainBinding.myRecyclerView.setLayoutManager(mLayoutManager);
        mainBinding.myRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("음식명 검색합니다.");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public void onSelected(FoodInfo item) {

    }
}
