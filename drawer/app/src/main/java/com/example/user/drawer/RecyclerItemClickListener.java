package com.example.user.drawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, MotionEvent e);

        void onLongItemClick(View view, int position);
    }

    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener){
        mListener = listener;
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
             public void  onLongPress(MotionEvent e){
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null && mListener != null){
                    Log.d("long", "press");
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {

        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e)){
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView), e);
            Log.d("1", "position1 =>" + view.getChildAdapterPosition(childView) );

            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) { }
}