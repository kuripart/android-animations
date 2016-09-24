package com.example.kuri.gestures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.support.v4.view.GestureDetectorCompat;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{


    private TextView text;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.dodo);
        this.gestureDetector = new GestureDetectorCompat(this,this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    // control + enter = implement methods (of interface)

    //start gestures//

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        text.setText("onSingleTap");
        return true; //event handed
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        text.setText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        text.setText("onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        text.setText("onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        text.setText("onShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        text.setText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        text.setText("onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        text.setText("onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        text.setText("onFling");
        return true;
    }

    //end gestures//

    // default function that gets called when a user touches the screen. Just checks for common touches
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //at first detect if there were any special gestures, if not continue as normal by returning.
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
