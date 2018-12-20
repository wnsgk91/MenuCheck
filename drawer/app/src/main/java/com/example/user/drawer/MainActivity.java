
package com.example.user.drawer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drawer.databinding.ActivityMainBinding;
import com.example.user.drawer.db.DBHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    Bitmap bitmap;


    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference image = database.getReference("image");

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;

    boolean isPermission = false;

    double lat;
    double lon;

    private GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bingding data
        mainBinding = DataBindingUtil.setContentView(this, activity_main);
        //toolbar
        toolbar = findViewById(R.id.t1_custom);
        toolbar.setTitle("Menu Check");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        mainBinding.myRecyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(myDataset, this);
        mainBinding.myRecyclerView.setAdapter(adapter);
        mainBinding.myRecyclerView.setLayoutManager(mLayoutManager);
        mainBinding.myRecyclerView.setItemAnimator(new DefaultItemAnimator());

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

        Button map1 = (Button) findViewById(R.id.map_res);
        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(MainActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    lat = gps.getLatitude();
                    lon = gps.getLongitude();

                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + lat + "\n경도: " + lon,
                            Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }

                final Intent intent = new Intent(getApplicationContext(), map_res.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);
            }
        });




        List<String> cateogires = new ArrayList<String>();
        cateogires.add("한국어");
        cateogires.add("English");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cateogires);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        drawerLayout.setDrawerListener(myDrawerListener);
        drawerView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event){
                return true;
            }
        });
        callPermission();

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

        mainBinding.myRecyclerView.addOnItemTouchListener(

            new RecyclerItemClickListener(getApplicationContext(), mainBinding.myRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                public void onItemClick(View view, int position, MotionEvent e) {
                    View child = mainBinding.myRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child != null){
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

    private void setData() {

        myDataset.add(new FoodInfo("구이(Grilled)",R.drawable.grilled));
        myDataset.add(new FoodInfo("국(Soup)",R.drawable.soup));
        myDataset.add(new FoodInfo("떡(Rice-Cake)",R.drawable.ricecake));
        myDataset.add(new FoodInfo("만두(Dumpling)",R.drawable.dumpling));
        myDataset.add(new FoodInfo("밥(Rice)",R.drawable.rice));
        myDataset.add(new FoodInfo("상차림(Korean-Table)",R.drawable.table));
        myDataset.add(new FoodInfo("전(Korean-Pancake)",R.drawable.pancake));
        myDataset.add(new FoodInfo("전골(Stew)",R.drawable.stew));
        myDataset.add(new FoodInfo("조림(Boiled)",R.drawable.boiled));
        myDataset.add(new FoodInfo("찜(Steamed)",R.drawable.steamed));

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
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(intent);
                return true;
            case R.id.action_more:
                // drawerLayout.openDrawer(drawerView);
                drawerLayout.openDrawer(drawerView);
                return true;
            case R.id.action_camera:
                startActivity(new Intent(this, Camera.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //spinner method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

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

    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

}
