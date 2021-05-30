package com.fbm.escape.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.fbm.escape.busmessage.FingerMovingMsg;
import com.fbm.escape.utils.ScreenSize;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PlayerDot extends SurfaceView implements SurfaceHolder.Callback {
    private final static String TAG = PlayerDot.class.getName();

    private final static float SAFE_DISTANCE=10F;

    private Context context;

    private float locationX;

    private float locationY;

    private Canvas canvas;

    private Paint paint;

    private float radius = 50;

    private SurfaceHolder surfaceHolder;

    boolean isOverTop;

    boolean isOverBottom;

    boolean isOverLeft;

    boolean isOverRight;

    public PlayerDot(Context context) {
        super(context);
        init(context);
    }

    public PlayerDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "PlayerDot init");
        this.context = context;
        EventBus.getDefault().register(this);
        setZOrderOnTop(true);

        locationX=ScreenSize.getWidth()/2;
        locationY= (float) (ScreenSize.getHeight()*0.2);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = false)
    public void updateLocation(Object object) {
        Log.i(TAG, "finger position received");
        if (object instanceof FingerMovingMsg) {
            updatePosition((FingerMovingMsg) object);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        EventBus.getDefault().unregister(this);
    }

    public void updatePosition( FingerMovingMsg fingerMovingMsg){
        isOverLeft=locationX - fingerMovingMsg.getDistanceX()-radius-SAFE_DISTANCE<0;
        isOverRight= ScreenSize.getWidth()-locationX + fingerMovingMsg.getDistanceX()-radius-SAFE_DISTANCE<0;
        isOverTop=locationY - fingerMovingMsg.getDistanceY()-radius-SAFE_DISTANCE<0;
        isOverBottom=ScreenSize.getHeight()*2/5-locationY + fingerMovingMsg.getDistanceY()-radius-SAFE_DISTANCE<0;

        if(!isOverLeft&&!isOverRight){
            locationX = locationX - fingerMovingMsg.getDistanceX();
        }
        if(!isOverTop&&!isOverBottom){
            locationY = locationY - fingerMovingMsg.getDistanceY();
        }
        Log.i(TAG,"location="+locationX+" "+locationY);
        draw();
    }

    private void draw() {
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawCircle(locationX, locationY, radius, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
