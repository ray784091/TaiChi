package com.fbm.escape.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder surfaceHolder;

    private Canvas canvas;

    private boolean isDrawing = true;

    public CustomSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        isDrawing = true;
        init();
        draw();
      //  new Thread(this).start();
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
            render(canvas, getPaint());
            surfaceHolder.unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(){
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        render(canvas, getPaint());
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public abstract void init(Context context);

    public abstract void render(Canvas canvas, Paint paint);

    public abstract Paint getPaint();

    public abstract void processEvent(Object object);

    public abstract float getSafeDistance();
}
