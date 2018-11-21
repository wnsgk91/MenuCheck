
package com.example.user.drawer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drawer.databinding.ActivityMainBinding;
import com.example.user.drawer.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.drawer.R.layout.activity_main;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    //sqlite
    private DBHelper dbHelper;

    //recycle view
    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;
    ArrayList<FoodInfo> myDataset = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);


    //drawer
    private DrawerLayout drawerLayout;
    private View drawerView;

    //toolbar
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bingding data
        mainBinding = DataBindingUtil.setContentView(this, activity_main);
        //toolbar
        toolbar = findViewById(R.id.t1_custom);
        toolbar.setTitle("Menu Check");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        setData();

        //toolbar favorite

        Button Favorite = (Button) findViewById(R.id.Favorite);
        Favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), FavoriteList.class);
                startActivity(intent);
            }
        });

        //spinner
        Spinner dropdown = findViewById(R.id.spinner);
        dropdown.setOnItemSelectedListener(this);


        List<String> cateogires = new ArrayList<String>();
        cateogires.add("한국어");
        cateogires.add("English");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cateogires);

        dropdown.setAdapter(dataAdapter);

        //drawer
        //--------------------------------------------------------------
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        Button buttonCloseDrawer = (Button) findViewById(R.id.closedrawer);
        buttonCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(myDrawerListener);
        drawerView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event){
                return true;
            }
        });

    }

    DrawerLayout.DrawerListener myDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View view, float v) { }
        @Override
        public void onDrawerOpened(@NonNull View view) { }
        @Override
        public void onDrawerClosed(@NonNull View view) { }
        @Override
        public void onDrawerStateChanged(int i) {
        }
    };

    private void setRecyclerView(){

        final Intent intent = new Intent(this, FoodList.class);

        mainBinding.myRecyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(myDataset, MainActivity.this);
        mainBinding.myRecyclerView.setAdapter(adapter);
        mainBinding.myRecyclerView.setLayoutManager(mLayoutManager);
        mainBinding.myRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mainBinding.myRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), mainBinding.myRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    public void onItemClick(View view, int position, MotionEvent e) {
                        View child = mainBinding.myRecyclerView.findChildViewUnder(e.getX(), e.getY());
                        if(child != null){
                            Log.d("1", "position2 =>" + mainBinding.myRecyclerView.getChildAdapterPosition(child) );
                            TextView name = mainBinding.myRecyclerView.getChildViewHolder(child).itemView.findViewById(R.id.name);
                            intent.putExtra("type", name.getText());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), position+"번 째 아이템 클릭", Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    private void setData(){

        myDataset.add(new FoodInfo("밥"));
        myDataset.add(new FoodInfo("상차림"));
        myDataset.add(new FoodInfo("찜"));
        myDataset.add(new FoodInfo("구이"));
        myDataset.add(new FoodInfo("전골"));
        myDataset.add(new FoodInfo("조림"));
        myDataset.add(new FoodInfo("전"));
        myDataset.add(new FoodInfo("떡"));
        myDataset.add(new FoodInfo("만두"));
        myDataset.add(new FoodInfo("국"));
        setRecyclerView();

    }



    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.action_search:
                Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(intent);
                return true;
            case R.id.action_more:
                Toast.makeText(this, "more", Toast.LENGTH_LONG).show();
                // drawerLayout.openDrawer(drawerView);
                drawerLayout.openDrawer(drawerView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //spinner method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "Selected : " + item, Toast.LENGTH_SHORT).show();

        switch (item){
            case "한국어" :
                //setLocale("ko");
                break;
            case "English" :
                //setLocale("en_US");
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
