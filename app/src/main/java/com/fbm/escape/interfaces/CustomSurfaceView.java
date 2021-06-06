package com.fbm.escape.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.fbm.escape.busmessage.FingerMovingMsg;
import com.fbm.escape.entity.Location;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static String TAG = CustomSurfaceView.class.getName();

    private SurfaceHolder surfaceHolder;

    private Canvas canvas;

    private boolean isDrawing = true;

    private boolean isOverTop;

    private boolean isOverBottom;

    private boolean isOverLeft;

    private boolean isOverRight;

    private Location location;

    public CustomSurfaceView(Context context) {
        super(context);
        init(context);
        init();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        init();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        isDrawing = true;
        location = new Location((float) getWidth() / 2, (float)getHeight()/2);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        isDrawing = false;
        EventBus.getDefault().unregister(this);
    }

    protected void init() {
        EventBus.getDefault().register(this);

        setZOrderOnTop(true);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = false)
    public void processEvents(Object object) {
        processEvent(object);
    }

    @Override
    public void run() {
        while (isDrawing) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            processLocation();
            render(canvas, location);
            surfaceHolder.unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processLocation() {
        FingerMovingMsg fingerMovingMsg = getFingerMovingMsg();
        if (fingerMovingMsg == null) {
            return;
        }

        isOverLeft = location.getX() < getLeft() + 50;
        isOverRight = location.getX() > getRight() - 50;
        isOverTop = location.getY() < getTop() + 50;
        isOverBottom = location.getY() > getBottom() - 50;

        float distanceX = fingerMovingMsg.getDistanceX();
        float distanceY = fingerMovingMsg.getDistanceY();

        if (isOverLeft && fingerMovingMsg.getDistanceX() >= 0) {
            distanceX = 0;
        }

        if (isOverRight && fingerMovingMsg.getDistanceX() <= 0) {
            distanceX = 0;
        }

        if (isOverTop && fingerMovingMsg.getDistanceY() >= 0) {
            distanceY = 0;
        }

        if (isOverBottom && fingerMovingMsg.getDistanceY() <= 0) {
            distanceY = 0;
        }

        location.setX(location.getX() - distanceX);
        location.setY(location.getY() - distanceY);
    }


    public abstract void init(Context context);

    public abstract void render(Canvas canvas, Location location);

    public abstract void processEvent(Object object);

    public abstract FingerMovingMsg getFingerMovingMsg();
}
