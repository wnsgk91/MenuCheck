package com.example.user.drawer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.user.drawer.FoodInfo;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FOODS = "Foods";
    private static final String DATABASE_NAME = "favorite";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FOODS = "CREATE TABLE " + TABLE_FOODS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE_FOODS);

        Toast.makeText(context, "Table 생성 완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void addFood(FoodInfo foodInfo){
        //  can write database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, foodInfo.getName());

        db.insert(TABLE_FOODS, null, values);
        Toast.makeText(context, values.toString(), Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodInfo> getAll(){
        ArrayList<FoodInfo> foodlist = new ArrayList<FoodInfo>();
        String SELECT_ALL = "SELECT * FROM " + TABLE_FOODS;
        //String SELECT_ALL = "DROP TABLE " + TABLE_FOODS;
        //String SELECT_ALL = "CREATE TABLE " + TABLE_FOODS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL" + ");";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if (cursor.moveToFirst()){
            do {
                FoodInfo food = new FoodInfo();
                food.setName(cursor.getString(1));
                foodlist.add(food);
            }while (cursor.moveToNext());
        }
        return foodlist;
    }

    public void delFood(FoodInfo foodInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = foodInfo.getName();
        values.put(KEY_NAME, name);

        db.delete(TABLE_FOODS, KEY_NAME + "=?", new String[]{name});

        //Toast.makeText(context, "delete 성공.", Toast.LENGTH_SHORT).show();
    }


}