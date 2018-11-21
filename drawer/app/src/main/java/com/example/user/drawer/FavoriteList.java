package com.example.user.drawer;

        import android.app.Activity;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.widget.ListView;

        import com.example.user.drawer.db.DBHelper;

        import java.util.ArrayList;

public class FavoriteList extends Activity {

    ListView listView;
    FavoriteListAdapter favoriteListAdapter;
    ArrayList<FoodInfo> foods;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);
        listView = (ListView) findViewById(R.id.foods_listview);

        foods = new ArrayList<FoodInfo>();

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ArrayList<FoodInfo> favorite_list = dbHelper.getAll();


        for(FoodInfo object : favorite_list) {
            foods.add(object);
        }

        favoriteListAdapter = new FavoriteListAdapter(foods, getApplicationContext());
        listView.setAdapter(favoriteListAdapter);
    }
}
