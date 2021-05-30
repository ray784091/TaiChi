package com.fbm.escape.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.fbm.escape.R;
import com.fbm.escape.utils.GestureProcessor;
import com.fbm.escape.utils.ScreenSize;
import com.fbm.escape.view.PlayerDot;

public class GameActivity extends BaseActivity {

    private static final String TAG = GameActivity.class.getName();
    private GestureDetector gestureDetector;
    private PlayerDot playerDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        init();

        GestureProcessor gestureProcessor = new GestureProcessor(this);
        gestureDetector = new GestureDetector(this, gestureProcessor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void init(){
        playerDot=findViewById(R.id.player);
        ViewGroup.LayoutParams layoutParams=playerDot.getLayoutParams();
        layoutParams.height= (int) ScreenSize.getHeight()*2/5;
    }

}