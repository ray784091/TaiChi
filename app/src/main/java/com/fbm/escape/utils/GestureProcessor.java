package com.fbm.escape.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.fbm.escape.activity.ExitActivity;
import com.fbm.escape.busmessage.FingerMovingMsg;

import org.greenrobot.eventbus.EventBus;

public class GestureProcessor implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = GestureProcessor.class.getName();

    private Context context;

    private FingerMovingMsg fingerMovingMsg;

    public GestureProcessor(Context context) {
        this.context = context;
        fingerMovingMsg =new FingerMovingMsg();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        fingerMovingMsg.setDistanceX(distanceX);
        fingerMovingMsg.setDistanceY(distanceY);
        EventBus.getDefault().post(fingerMovingMsg);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "onFling");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(TAG, "onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap");
        Intent intent=new Intent(context, ExitActivity.class);
        context.startActivity(intent);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent");
        return true;
    }
}
