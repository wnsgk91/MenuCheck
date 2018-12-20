package com.example.user.drawer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Dialog extends AppCompatActivity {

    ImageView imageView;
    Bitmap bitmap;
    TextView detailtext;
    TextView name;

    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference image = database.getReference("food_image");
    DatabaseReference detail = database.getReference("food_detail");

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.food_dialog);

        Intent intent = getIntent();
        String type = intent.getStringExtra("detail");
        setImage();
        setDetail();
    }



    public void close(View v){
        finish();
    }



    public boolean onTouch(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public void setDetail(){

        Intent intent = getIntent();
        final String type = intent.getStringExtra("detail");

        detailtext = (TextView) findViewById(R.id.detail);
        name = (TextView) findViewById(R.id.food_name);

        detail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.child(type).getKey();
                name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        detail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.child(type).getValue(String.class);
                detailtext.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setImage(){

        imageView = (ImageView) findViewById(R.id.food_image);

        Intent intent = getIntent();
        final String type = intent.getStringExtra("detail");





        image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                final String value = dataSnapshot.child(type).getValue(String.class);

                Thread mThread = new Thread(){
                    @Override
                    public void run() {

                        try{
                            URL url = new URL(value);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();

                            InputStream is = conn.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);
                        }catch (MalformedURLException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                };

                mThread.start();

                try{
                    mThread.join();
                    imageView.setImageBitmap(bitmap);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

}
