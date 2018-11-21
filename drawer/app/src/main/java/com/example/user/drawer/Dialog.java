package com.example.user.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Dialog extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.food_dialog);

        TextView detail = (TextView) findViewById(R.id.detail);
        Intent intent = getIntent();
        String type = intent.getStringExtra("detail");
        detail.setText(type+"에 대한 설명");
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

}
